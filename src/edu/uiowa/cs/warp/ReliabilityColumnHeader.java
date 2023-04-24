package edu.uiowa.cs.warp;

import java.util.ArrayList;

/**
 * Singleton class to generate the column headers for the reliability analysis
 * of a given Warp interface. Call getColumnHeader() to get the header in a
 * String array; it requires a parameter in case it has not been initialized yet.
 * 
 * @author Jackson Grant
 *
 */
public class ReliabilityColumnHeader {
	
	private static String[] columnHeader;
	
	private static WorkLoad system;
	
	private ReliabilityColumnHeader() {
		ArrayList<String> columnHeaderList = new ArrayList<String>(0);
		ArrayList<String> flowNames = system.getFlowNamesInPriorityOrder();
		for(String flow: flowNames) {
			String[] nodes = system.getNodesInFlow(flow);
			for(String node: nodes) {
				columnHeaderList.add(flow + ":" + node);
			}
		}
		int numCols = columnHeaderList.size();
		columnHeader = new String[numCols];
		columnHeader = columnHeaderList.toArray(columnHeader);
	}
	
	/**
	 * Returns the column headers properly formatted from the flows in
	 * the given Warp interface.
	 * 
	 * @param warp the WarpInterface to get the column header from
	 * @return a String array holding the column headers
	 */
	public static String[] getColumnHeader(WarpInterface warp) {
		return getColumnHeader(warp.toProgram());
	}
	
	/**
	 * Returns the column headers properly formatted from the flows in
	 * the given program.
	 * 
	 * @param program the Program to get the column header from
	 * @return a String array holding the column headers
	 */
	public static String[] getColumnHeader(Program program) {
		if(system == null) {
			system = program.toWorkLoad();
			new ReliabilityColumnHeader();
		}
		return columnHeader;
	}
}
