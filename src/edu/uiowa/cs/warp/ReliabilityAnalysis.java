package edu.uiowa.cs.warp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;
import edu.uiowa.cs.warp.WarpDSL.InstructionParameters;

/**
 * ReliabilityAnalysis analyzes the end-to-end reliability of messages transmitted in flows for the
 * WARP system.
 * <p>
 * 
 * Let M represent the Minimum Packet Reception Rate on an edge in a flow. The end-to-end
 * reliability for each flow, flow:src->sink, is computed iteratively as follows:<br>
 * (1)The flow:src node has an initial probability of 1.0 when it is released. All other initial
 * probabilities are 0.0. (That is, the reset of the nodes in the flow have an initial probability
 * value of 0.0.) <br>
 * (2) each src->sink pair probability is computed as NewSinkNodeState = (1-M)*PrevSnkNodeState +
 * M*PrevSrcNodeState <br>
 * This value represents the probability that the message has been received by the node SinkNode.
 * Thus, the NewSinkNodeState probability will increase each time a push or pull is executed with
 * SinkNode as a listener.
 * <p>
 * 
 * The last probability state value for any node is the reliability of the message reaching that
 * node, and the end-to-end reliability of a flow is the value of the last Flow:SinkNode
 * probability.
 * <p>
 * 
 * CS2820 Spring 2023 Project: Implement this class to compute the probabilities the comprise the
 * ReliablityMatrix, which is the core of the file visualization that is requested in Warp.
 * <p>
 * 
 * To do this, you will need to retrieve the program source, parse the instructions for each node,
 * in each time slot, to extract the src and snk nodes in the instruction and then apply the message
 * success probability equation defined above.
 * <p>
 * 
 * I recommend using the getInstructionParameters method of the WarpDSL class to extract the src and
 * snk nodes from the instruction string in a program schedule time slot.
 * 
 * @author sgoddard
 * @version 1.5
 *
 */
public class ReliabilityAnalysis {
	
	/**
	 * The program the analysis is being performed on.
	 */
	private Program program;
	
	/**
	 * The required end to end communication reliability for the flows.
	 */
	private Double e2e;
	
	/**
	 * Minimum packet reception rate for graph nodes.
	 */
	private Double minPacketReceptionRate;
	
	/**
	 * The number of faults per edge in a flow.
	 */
	private Integer numFaults;
	
	/**
	 * The number of pushes for each node and the worst case transmission time, used
	 * in numTxPerLinkAndTotalTxCost.
	 */
	private ArrayList<Integer> nPushes;
	
	/**
	 * A reliability table with the results of the reliability analysis. Rows
	 * in the table are the timeslots in the program schedule, and columns
	 * are each node in each flow, with the flows sorted by priority order.
	 */
	private ReliabilityTable reliabilities;
	
	/**
	 * The schedule for the input program.
	 */
	private ProgramSchedule schedule;
	
	/**
	 * The headers of all columns in the reliability table.
	 */
	private String[] headerRow;
	
	private WarpDSL dsl;
	
	private NodeMap nodeIndexes;
	  
	  
	
   /**
    * Creates a new ReliabilityAnalysis object with the provided values for
    * end-to-end communication and minimum packet reception rate. All other
    * values are set to defaults.
    * 
    * @param e2e The required end to end communication reliability for flows
    * @param minPacketReceptionRate The minimum packet reception rate for nodes
    */
   public ReliabilityAnalysis (Double e2e, Double minPacketReceptionRate) {
      this.setDefaultParameters();
      this.e2e = e2e;
      this.minPacketReceptionRate = minPacketReceptionRate;
   }
   
   /**
    * Creates a new ReliabilityAnalysis object with the provided values for
    * the number of faults. All other values are set to defaults.
    * 
    * @param numFaults The number of faults allowed per edge in a Flow
    */
   public ReliabilityAnalysis (Integer numFaults) {
      this.setDefaultParameters();
      this.numFaults = numFaults;
   }
   

  /**
   * Runs a reliability analysis on the given program and stores it in the
   * resulting object.
   * 
   * @param program The program to analyze the reliability of
   */
  public ReliabilityAnalysis(Program program) {
	  this.program = program;
	  this.e2e = program.getE2e();
	  this.minPacketReceptionRate = program.getMinPacketReceptionRate();
	  this.numFaults = program.getNumFaults();
	  this.schedule = program.getSchedule();
	  this.dsl = new WarpDSL();
	  
	  setReliabilityHeaderRow(ReliabilityColumnHeader.getColumnHeader(program));
	  
	  this.nodeIndexes = buildNodeMap(program.toWorkLoad());
	  
	  buildReliabilities();
  }
  
  /**
   * Sets all parameters to default values specified within this method. Used only for
   * initializing an analysis for numTxPerLinkAndTotalTxCost with ReliabilityAnalysis(numFaults)
   * and ReliabilityAnalysis(e2e, minPacketReceptionRate).
   */
  private void setDefaultParameters() {
	  this.e2e = 0.99;
	  this.minPacketReceptionRate = 0.9;
	  this.numFaults = 0;
  }
  
  /**
   * Builds a map mapping all column header values to a ReliabilityNode, which
   * is a node subclass indicating whether a node is a source, its phase, and
   * its index.
   * 
   * @param workload a WorkLoad to build a map from
   * @return a NodeMap mapping all column headers to ReliabilityNodes
   */
  private NodeMap buildNodeMap(WorkLoad workload) {
	  NodeMap nodeMap = new NodeMap();
	  
	  ArrayList<String> flowNames = workload.getFlowNamesInPriorityOrder();
	  FlowMap flowMap = workload.getFlows();
	  
	  int index = 0;
	  
	  //Iterate through list of flow names in priority order
	  for(String flowName: flowNames) {
		  //Get the current flow
		  Flow currentFlow = flowMap.get(flowName);
		  //Get the nodes in the current flow
		  ArrayList<Node> nodesInFlow = currentFlow.getNodes();
		  //Iterate through the list of nodes in the current flow
		  for(int i = 0; i < nodesInFlow.size(); i++) {
			  if(i == 0) {
				  String nodeName = this.getReliabilityHeaderRow()[index];
				  nodeMap.put(nodeName, new ReliabilityNode(index, nodesInFlow.get(i), true));
			  }else {
				  String nodeName = this.getReliabilityHeaderRow()[index];
				  nodeMap.put(nodeName, new ReliabilityNode(index, nodesInFlow.get(i), false));
			  }
			  index++;
		  }
	  }
	  
	  //Test that nodeMap is working next time you open this up
	  return nodeMap;
  }
  
  /**
   * Computes all reliabilities and fills in the reliability table for the given program.
   */
  public void buildReliabilities() {
	  ReliabilityTable reliabilities = new ReliabilityTable(schedule.size(), headerRow.length);
	  
	  
	  reliabilities = setInitialStateForReleasedFlows(nodeIndexes, reliabilities);
	  
	  //fillTable();
	  
	  //This is for testing and will be removed in the final version
	  printRATable(reliabilities);
  }

  private ReliabilityTable setInitialStateForReleasedFlows(NodeMap nodeMap, ReliabilityTable reliabilities) {
	  for(int col = 0; col < headerRow.length; col++) {
		  ReliabilityNode currentNode = (ReliabilityNode) nodeMap.get(headerRow[col]);
		  if(currentNode.isSource())
			  for(int row = 0; row < reliabilities.getNumRows(); row++) {
			  	reliabilities.set(row, currentNode.getIndex(), 1.0);
			  }
	  }
	  
	  return reliabilities;
  }
  
  private void carryFowardReliabilities(int timeslot, NodeMap nodemap, ReliabilityTable reliabilities) {
	//TODO implement this operation
  }
  
  private void setReliabilities(ReliabilityTable reliabilities) {
	//TODO implement this operation
  }

  /**
   * Fills the reliability table row by row. 
   * 
   * Currently there is an error when a flow is returned to. In example1a, this happens
   * on the 11th row, after F1 finishes and when F0 starts again. In the example output,
   * it looks like the flow gets reset to its original reliabilities before the push happens, but in
   * my version, it does not get reset and simply adds more pushes in F0.
   * 
   * The simple solution that comes to mind is to watch for when a different flow is visited than
   * the one that is currently being visited, and reset that flow's reliabilities when that happens.
   * However, I suspect there is a better solution that could be found with better understanding of 
   * what is happening in the expected output.
   */
  private void fillTable() {
	  //Set up objects to get instructions from
	  WarpDSL instructionGetter = new WarpDSL();
	  ArrayList<InstructionParameters> instructions;
	  int timeslot = 0;
	  
	  //Iterate through each row in the schedule, and for each row iterate through each set of
	  //instructions in it. For every instruction in the schedule, update the reliability 
	  //table if it is a push or pull command (indicating a transmission), and ignore sleep and
	  //wait commands.
	  for(int row = 0; row < schedule.getNumRows(); row++) {
		  copyPrevRow(timeslot);
		  for(int col = 0; col < schedule.getNumColumns(); col++) {
			  instructions = instructionGetter.getInstructionParameters(schedule.get(row,col));
			  for(InstructionParameters i:instructions) {
				  if(i.getName().equals("push") || i.getName().equals("pull")) {
					  updateTable(i.getFlow(), i.getSrc(), i.getSnk(), timeslot);
				  }
			  }
		  }
		  timeslot++;
	  }
	  
	  
	  /*
	  WarpDSL a = new WarpDSL();
	  System.out.println("Name, Flow, Src, Snk, Coordinator, Listener, Channel.\n");
	  for(var i: schedule) {
		  for(var j:i) {
			  System.out.println(j);
			  var b = a.getInstructionParameters(j);
			  for(var x: b) {
				  System.out.print(x.getName()+", ");
				  System.out.print(x.getFlow()+", ");
				  System.out.print(x.getSrc()+", ");
				  System.out.print(x.getSnk()+", ");
				  System.out.print(x.getCoordinator()+", ");
				  System.out.print(x.getListener()+", ");
				  System.out.print(x.getChannel()+".");
				  System.out.println("\n");
			  }
		  }
		  System.out.println("\n");
	  }
	  //System.out.println(schedule.toString());
	   * 
	   */
  }
  
  /**
   * Copies the previous row in the reliaiblity table to the current row.
   * 
   * @param timeslot the timeslot of the current row
   */
  private void copyPrevRow(int timeslot) {
	  if(timeslot > 0) {
		  for(int col = 0; col < reliabilities.getNumColumns(); col++) {
			  Double prev = reliabilities.get(timeslot-1, col);
			  reliabilities.set(timeslot, col, prev);
		  }
	  }
  }
  
  /**
   * Updates a cell the the reliability table following transmission between nodes.
   * 
   * @param flow the flow of the transmission
   * @param source the source node
   * @param sink the sink node, which is the node to be updated
   * @param timeslot the current timeslot
   */
  private void updateTable(String flow, String source, String sink, int timeslot) {
	  //Find the index of the column that needs to be updated
	  String sinkColumn = flow + ":" + sink;
	  int colIndex = -1;
	  for(int col = 0; col < headerRow.length; col++) 
		  if(headerRow[col].equals(sinkColumn))
			  colIndex = col;
	  
	  //Update the cell based on the reliability math
	  if(timeslot < 1) {
		  reliabilities.set(timeslot, colIndex, minPacketReceptionRate);
	  }else {
		  Double prevSrcNodeState = reliabilities.get(timeslot-1, colIndex-1);
		  Double prevSinkNodeState = reliabilities.get(timeslot-1, colIndex);
		  
		  Double newSinkNodeState = (1-minPacketReceptionRate) * prevSinkNodeState
				  + minPacketReceptionRate * prevSrcNodeState;
		  
		  reliabilities.set(timeslot, colIndex, newSinkNodeState);
	  }
  }
  
  
  public void setReliabilityHeaderRow(String[] headerRow) {
	this.headerRow = headerRow;
  }
  
  public String[] getReliabilityHeaderRow() {
	return headerRow;
  }
  
  public void getFinalReliabilityRow() {
	  //TODO implement this operation
  }
  
  /**
   * Prints the table of computed reliabilities to the console.
   */
  public void printRATable(ReliabilityTable reliabilities) {
	for(String name: headerRow) {
		System.out.print(name+"\t");
	}
	System.out.println();
	for(int row = 0; row < reliabilities.getNumRows(); row++) {
		for(int col = 0; col < reliabilities.getNumColumns(); col++) {
			System.out.print(reliabilities.get(row,col)+"\t");
		}
		System.out.println();
	}
  }
  
  /**
   * Returns a table with the results of the reliability analysis.
   * Rows in the table are the timeslots in the program schedule,
   * and columns in the table are each node in each flow, with the 
   * flows sorted by priority order.
   * 
   * 
   * @return a ReliabilityTable with the results of the analysis
   */
  public ReliabilityTable getReliabilities() {
      return reliabilities;
   }

  /**
   * 
   * 
   * @return true if reliabilities have been met, false if not
   */
  public Boolean verifyReliabilities() {
    // TODO Auto-generated method stub
    return true;
  }



  /*
   * Testing main, feel free to rewrite and/or use whenever you need to test something.
   * If you want to save a test for reuse, just comment it out when it's not in use.
   * 
   * TODO delete this
   */
  public static void main(String[] args) {
	  WorkLoad workload = new WorkLoad(0.8, 0.99, "Example1a.txt");
	  Program program = new Program(workload, 16, ScheduleChoices.PRIORITY);
	  
	  //Node node = program.toWorkLoad().getNodes().get(workload.getNodeNamesOrderedAlphabetically()[0]);
	  //System.out.println(node.getStartTime());
	  ReliabilityAnalysis tester = new ReliabilityAnalysis(program);
	  
	  
  }
  
  
  /**
   * Calculates number of transmissions needed per link and total worst-case
   * transmission time for the given flow.
   * 
   * @param flow the flow being analyzed
   * @return an ArrayList containing the number of transmissions per link and the
   * total worst case time of transmitting end to end at the end of the List.
   */
  public ArrayList<Integer> numTxPerLinkAndTotalTxCost(Flow flow) {
	  if(numFaults > 0) {
		  return getFixedTxPerLinkAndTotalTxCost(flow);
	  }
      
      ArrayList<Node> nodesInFlow = flow.getNodes();
      
      int nNodesInFlow = nodesInFlow.size();
      
      /*Array to track nPushes for each node in this flow (same as nTx per link).
      Initialized to all 0 values.
      The last entry will contain the worst-case cost of transmitting E2E in 
      isolation*/
      nPushes = new ArrayList<Integer>(Collections.nCopies(nNodesInFlow + 1, 0));
      
      int nHops = nNodesInFlow - 1;
      
      /*minLinkReliablityNeded is the minimum reliability needed per link in a flow to hit E2E
      * reliability for the flow.
      * Use max to handle rounding error when e2e == 1.0 */
      Double minLinkReliablityNeeded = Math.max(e2e, Math.pow(e2e, (1.0 / (double) nHops))); 
      
      
      /* Now compute reliability of packet reaching each node in the given time slot:
       *Start with a 2-D reliability window that is a 2-D matrix of no size
       *each row is a time slot, stating at time 0
       *each column represents the reliability of the packet reaching that node at the
       *current time slot (i.e., the row it is in)
       *will add rows as we compute reliabilities until the final reliability is reached
       *for all nodes. */
      ReliabilityTable reliabilityWindow = new ReliabilityTable();
      
      ReliabilityRow nextRow = new ReliabilityRow(nNodesInFlow, 0.0);
      // initialize (i.e., P (packet@FlowSrc = 1)
      nextRow.set(0, 1.0);
      // the analysis will end when the e2e reliability metric is met, initially the
      // state is not met and will be 0 with this statement
      Double e2eReliabilityState = nextRow.get(nNodesInFlow - 1);
      
      
      int timeSlot = 0;
      ReliabilityRow prevReliabilityRow = new ReliabilityRow(nNodesInFlow, 0.0);
      
      while (e2eReliabilityState < e2e) {
    	  Collections.copy(prevReliabilityRow, nextRow);
    	  
    	  nextRow = nextRow(prevReliabilityRow, minLinkReliablityNeeded);
          
    	  e2eReliabilityState = nextRow.get(nNodesInFlow - 1);
          
    	  reliabilityWindow.add(nextRow);
          
    	  timeSlot += 1;
      
      }
	  
	  // The total (worst-case) cost to transmit E2E in isolation with
      // specified reliability target is the number of rows in the reliabilityWindow,
      // which is equal to the number of time slots.
      int time = timeSlot;
	  nPushes.set(nNodesInFlow, time);
	  return nPushes;
   }
  
  
  /**
   * Computes the next row in the reliability window.
   * 
   * @param prevReliabilityRow The previous reliability row in the reliability window
   * @param minLinkReliablityNeeded The minimum reliability needed for each link in the path
   * @return the next reliability row in the reliability window
   */
  private ReliabilityRow nextRow(ReliabilityRow prevReliabilityRow,
		  double minLinkReliablityNeeded) {
	  
	  int nNodesInFlow = nPushes.size()-1;
	  ReliabilityRow currentReliabilityRow = new ReliabilityRow(nNodesInFlow, 0.0);
	  Collections.copy(currentReliabilityRow, prevReliabilityRow);
	  
	  for (int nodeIndex = 0; nodeIndex < (nNodesInFlow - 1); nodeIndex++) {
		  int flowSrcNodeindex = nodeIndex;
		  int flowSnkNodeindex = nodeIndex + 1;
		  Double prevSrcNodeState = prevReliabilityRow.get(flowSrcNodeindex);
		  Double prevSnkNodeState = prevReliabilityRow.get(flowSnkNodeindex);
		  Double nextSnkState;
	  
		  // do a push until PrevSnk state > e2e to ensure next node reaches target E2E BUT
		  // skip if no chance of success (i.e., source doesn't have packet)
		  if ((prevSrcNodeState > 0 && prevSnkNodeState < minLinkReliablityNeeded)) {
		  
		  		//need to continue attempting to Tx, so update current state
		  		nextSnkState = ((1.0 - minPacketReceptionRate) * prevSnkNodeState) + 
		  					(minPacketReceptionRate * prevSrcNodeState);
		  		// increment the number of pushes for for this node to snk node
		  		nPushes.set(nodeIndex, nPushes.get(nodeIndex) + 1);
		  } else {
      			// snkNode has met its reliability. Thus move on to the
    	  		// next node and record the reliability met
      			nextSnkState = prevSnkNodeState;
		  }
		  
		  currentReliabilityRow.set(flowSnkNodeindex, nextSnkState);
	  }
	  return currentReliabilityRow;
  }
  
  /**
   * Calculates the fixed number of transmissions per link based on the number of faults per edge
   * and the total number of transmissions for the flow.
   * 
   * @param flow the flow being analyzed
   * @return an ArrayList containing the number of transmissions for each node with the total
   * number of transmissions at the end of the list
   */
  private ArrayList<Integer> getFixedTxPerLinkAndTotalTxCost(Flow flow){
	  ArrayList<Node> nodesInFlow = flow.getNodes();
	  int nNodesInFlow = nodesInFlow.size();
	  ArrayList<Integer> txArrayList = new ArrayList<Integer>(nNodesInFlow + 1);
	  /*
	   * Each node will have at most numFaults+1 transmissions. Because we don't know which nodes will
	   * send the message over an edge, we give the cost to each node.
	   */
	  for (int i = 0; i < nNodesInFlow; i++) {
	    txArrayList.add(numFaults + 1);
	  }
	  /*
	   * now compute the maximum # of TX, assuming at most numFaults occur on an edge per period, and
	   * each edge requires at least one successful TX.
	   */
	  int numEdgesInFlow = nNodesInFlow - 1;
	  int maxFaultsInFlow = numEdgesInFlow * numFaults;
	  txArrayList.add(numEdgesInFlow + maxFaultsInFlow);
	  return txArrayList;
  }
}
