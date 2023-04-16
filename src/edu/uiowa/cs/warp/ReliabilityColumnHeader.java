package edu.uiowa.cs.warp;

import java.util.ArrayList;
import java.util.MissingResourceException;

/**
 * Singleton class to generate the column headers for the reliability analysis
 * of a given Warp interface. Call getColumnHeader() to get the header in a
 * String array; must pass a WarpInterface the first time this method is called.
 * 
 * @author jgrant6
 *
 */
public class ReliabilityColumnHeader {

	private static String[] columnHeader;
	
	private static WarpInterface system;
	
	private ReliabilityColumnHeader() {
		WorkLoad workload = system.toWorkload();
		ArrayList<String> columnHeaderList = new ArrayList<String>(0);
		ArrayList<String> flowNames = workload.getFlowNamesInPriorityOrder();
		for(String flow: flowNames) {
			String[] nodes = workload.getNodesInFlow(flow);
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
		if(system == null) {
			system = warp;
			new ReliabilityColumnHeader();
		}
		return columnHeader;
	}
	
	
	//Temporary workaround for RA, definitely needs to be fixed.
	/**
	 * Returns the column headers properly formatted from the flows in
	 * the given Warp interface if an interface has been previously given.
	 * 
	 * @return a String array holding the column headers
	 */
	public static String[] getColumnHeader() {
		if(system == null) {
			throw new MissingResourceException("No warp interface to grab flow information from",
					"WarpInterface", "warp");
		}
		return columnHeader;
	}
}
