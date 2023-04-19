package edu.uiowa.cs.warp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

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
	
	private ProgramSchedule schedule;
	  
	  
	
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
	  buildReliabilities();
  }
  
  /**
   * Sets all parameters to default values specified within this method.
   */
  private void setDefaultParameters() {
	  this.e2e = 0.99;
	  this.minPacketReceptionRate = 0.9;
	  this.numFaults = 0;
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
  
  /**
   * Computes all reliabilities and fills in the reliability table
   */
  public void buildReliabilities() {
	// TODO implement this operation
	  System.out.println(schedule.toString());
  }
  
  public void setHeaderRow() {
	// TODO implement this operation
  }
  
  public void getHeaderRow() {
	// TODO implement this operation
  }
  
  /**
   * Prints the table of computed reliabilities to the console.
   */
  public void printRATable() {
	// TODO implement this operation
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
   * Verifies that the reliability requirement has been met for all flows in the 
   * current program.
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
	  WorkLoad workload = new WorkLoad(0.8, 0.99, "Example.txt");
	  Program program = new Program(workload, 16, ScheduleChoices.PRIORITY);
	  ReliabilityAnalysis tester = new ReliabilityAnalysis(program);
//	  Flow testingFlow = test.getFlows().get("F0");
	  //test.numTxAttemptsPerLinkAndTotalTxAttempts(testingFlow, 0.99, 0.9, false);
	  //tester.numTxPerLinkAndTotalTxCost(testingFlow);
  }
}
