package edu.uiowa.cs.warp;

/**
 * A Node subclass used for a reliability analysis. Nodes in different columns of the
 * ReliabilityTable need to be distinguished from each other, even if they are the same
 * node being used by different flows. ReliabilityNodes hold basic information from
 * their original Node, as well as whether or not they are a source of a flow, the
 * index of their column in the ReliabilityTable, and their phase.
 * 
 * @author Jackson Grant
 *
 */

public class ReliabilityNode extends Node {
	private boolean isSource;
	  
	private int columnIndex;
	  
	private int phase;
	
	/**
	 * Creates a new ReliabilityNode to be used in a ReliabilityAnalysis NodeMap.
	 * 
	 * @param index The column index of the node in the ReliabilityTable
	 * @param node The Node to build this ReliabilityNode from
	 * @param isSource True if this node is a source in a flow, false if not
	 */
	public ReliabilityNode(int index, Node node, boolean isSource) {
		super(node.getName(), node.getPriority(), node.getIndex());
		this.columnIndex = index;
		this.phase = node.getPhase();
		this.isSource = isSource;
	}
	  
	/**
	 * @return true is the node is a source, false if not
	 */
	public boolean isSource() {
		return isSource;
	}

	/**
	 * @return the column index of the node in the reliability table
	 */
	public int getColumnIndex() {
		return columnIndex;
	}	  
	
	/**
	 * @return the phase of the node
	 */
	public Integer getPhase() {
		return phase;
	}
	
}
