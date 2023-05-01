package edu.uiowa.cs.warp;

import java.util.ArrayList;

public class ReliabilityNode extends Node {
	private boolean isSource;
	  
	private int columnIndex;
	  
	private int phase;
	
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

	public int getColumnIndex() {
		return columnIndex;
	}	  
	public Integer getPhase() {
		return phase;
	}
	
	//for testing
	public String toString() {
		return "name: " + this.getName() + "isSource: " + isSource + " columnIndex: " + columnIndex + " phase: " + phase;
	}
}
