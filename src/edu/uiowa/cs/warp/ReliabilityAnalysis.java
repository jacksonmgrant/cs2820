package edu.uiowa.cs.warp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

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
 * This value represents the probability that the message as been received by the node SinkNode.
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
   


  public ReliabilityAnalysis(Program program) {
    // TODO Auto-generated constructor stub
	// Not implemented
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
   * Calculates number of transmissions needed per link and total transmissions
   * required for the given flow
   * 
   * @param flow the flow being analyzed
   * @return an ArrayList containing the number of transmissions per link and the
   * total worst case cost of transmitting end to end at the end of the List.
   */
  public List<Integer> numTxPerLinkAndTotalTxCost(Flow flow) {
      // TODO implement this operation
      
      ArrayList<Node> nodesInFlow = flow.getNodes();
      //The last entry will contain the worst-case cost of transmitting E2E in 
      //isolation
      int nNodesInFlow = nodesInFlow.size();
      
      //Array to track nPushes for each node in this flow (same as nTx per link).
      //Initialized to all 0 values.
      List<Integer> nPushes = new ArrayList<Integer>(Collections.nCopies(nNodesInFlow + 1, 0));
      
      int nHops = nNodesInFlow - 1;
      // minLinkReliablityNeded is the minimum reliability needed per link in a flow to hit E2E
      // reliability for the flow
      Double minLinkReliablityNeeded = Math.max(e2e, Math.pow(e2e, (1.0 / (double) nHops))); 
      // use max to handle rounding error when e2e == 1.0
      
      
      
      //Make a helper method?
      
      /* Now compute reliability of packet reaching each node in the given time slot:
       *Start with a 2-D reliability window that is a 2-D matrix of no size
       *each row is a time slot, stating at time 0
       *each column represents the reliability of the packet reaching that node at the
       *current time slot (i.e., the row it is in)
       *will add rows as we compute reliabilities until the final reliability is reached
       *for all nodes. */
      ReliabilityTable reliabilityWindow = new ReliabilityTable();
      ReliabilityRow newReliabilityRow = new ReliabilityRow();
      // create the row initialized with 0.0 values
      for (int i = 0; i < nNodesInFlow; i++) { 
    	  newReliabilityRow.add(0.0);
      }
      
      reliabilityWindow.add(newReliabilityRow);
      ReliabilityRow currentReliabilityRow = reliabilityWindow.get(0);
      // var currentReliabilityRow = (Double[]) reliabilityWindow.get(0).toArray();
      // Want reliabilityWindow[0][0] = 1.0 (i.e., P(packet@FlowSrc) = 1
      // but I din't want to mess with the newReliablityRow vector I use below
      // So, we initialize this first entry to 1.0, which is reliabilityWindow[0][0]
      // We will then update this row with computed values for each node and put it
      // back in the matrix
      
      // initialize (i.e., P (packet@FlowSrc = 1)
      currentReliabilityRow.set(0, 1.0);
      // the analysis will end when the e2e reliability metric is met, initially the
      // state is not met and will be 0 with this statement
      Double e2eReliabilityState = currentReliabilityRow.get(nNodesInFlow - 1);
      
      
      // start time at 0
      int timeSlot = 0;
      ReliabilityRow prevReliabilityRow = new ReliabilityRow(nNodesInFlow, 0.0);
      
      while (e2eReliabilityState < e2e) {
    	  Collections.copy(prevReliabilityRow, currentReliabilityRow);
    	  // would be reliabilityWindow[timeSlot] if working through a schedule
    	  Collections.copy(currentReliabilityRow, newReliabilityRow);
      
      
    	  // Now use each flow:source->sink to update reliability computations
    	  // this is the update formula for the state probabilities
    	  // nextState = (1-M) * prevState + M*NextHighestFlowState
    	  // use minLQ for M in above equation
    	  // NewSinkNodeState = (1-M)*PrevSnkNodeState + M*PrevSrcNodeState
      
    	  // loop through each node in the flow and update the states for
    	  // each link (i.e., sink->src pair)
    	  for (int nodeIndex = 0; nodeIndex < (nNodesInFlow - 1); nodeIndex++) {
    		  int flowSrcNodeindex = nodeIndex;
    		  int flowSnkNodeindex = nodeIndex + 1;
    		  Double prevSrcNodeState = prevReliabilityRow.get(flowSrcNodeindex);
    		  Double prevSnkNodeState = prevReliabilityRow.get(flowSnkNodeindex);
    		  Double nextSnkState;
    	  
    		  // do a push until PrevSnk state > e2e to ensure next node reaches target E2E BUT
    		  // skip if no chance of success (i.e., source doesn't have packet)
    		  if ((prevSnkNodeState < minLinkReliablityNeeded) && prevSrcNodeState > 0) {
    		  
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
    	  
    		  // probabilities are non-decreasing so update if we were higher by carrying old
    		  // value forward
    		  if (currentReliabilityRow.get(flowSrcNodeindex) < 
    				  prevReliabilityRow.get(flowSrcNodeindex)) { 
          	
    			  // carry forward the previous state for the src node, which may get over written
    			  // later by another instruction in this slot
    			  currentReliabilityRow.set(flowSrcNodeindex, 
    					  prevReliabilityRow.get(flowSrcNodeindex));
    		  }
    		  currentReliabilityRow.set(flowSnkNodeindex, nextSnkState);
    	  }
          
    	  e2eReliabilityState = currentReliabilityRow.get(nNodesInFlow - 1);
          
    	  reliabilityWindow.add(currentReliabilityRow);
          
    	  timeSlot += 1; // increase to next time slot
      
      }
	  
	  // The total (worst-case) cost to transmit E2E in isolation with
      // specified reliability target is the number of rows in the reliabilityWindow
      int size = reliabilityWindow.size();
	  nPushes.set(nNodesInFlow, size); 
	  return nPushes;
   }

  
  
  /*
   * Testing main, feel free to rewrite and/or use whenever you need to test something.
   * If you want to save a test for reuse, just comment it out when it's not in use.
   * 
   * TODO delete this
   */
  public static void main(String[] args) {
	  ReliabilityAnalysis tester = new ReliabilityAnalysis(1);
	  WorkLoad test = new WorkLoad(0.9, 0.99, "Example.txt");
	  Flow testingFlow = test.getFlows().get("F0");
	  tester.numTxPerLinkAndTotalTxCost(testingFlow);
  }
  
  
  
  
  
  
  public ReliabilityTable getReliabilities() {
      // TODO implement this operation
      throw new UnsupportedOperationException("not implemented");
   }

  public Boolean verifyReliabilities() {
    // TODO Auto-generated method stub
    return true;
  }

}
