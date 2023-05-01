package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

/** 
 * Tests the class ReliabilityAnalysis to make sure the methods are properly set up. In these tests, Jackie wrote the
 * testVerifyReliabilities(), testCarryForwardReliabilities(), testSetReliabilities(), testSetReliabilityHeaderRow().
 * Matt wrote the test for testGetReliabilities(), testBuildReliabilityTable(), testPrintRaTable(), and
 * testSetInitialStateForReleasedFlows().
 * 
 * @author Jackie Mills
 * @author Matt Boenish
 *
 */
class ReliabilityAnalysisTest {

	private WorkLoad workload;
	private Program program;
	private WarpInterface warp;
	private ReliabilityAnalysis tester;
	String[][] expectedData = {{"1","0.8","0","1","0","0"},{"1","0.96","0.64","1","0","0"},
			  				  {"1","0.992","0.896","1","0","0"},{"1","0.9984","0.9728","1","0","0"},{"1","0.9984","0.99328","1","0","0"},
			  				  {"1","0.9984","0.99328","1","0.8","0"},{"1","0.9984","0.99328","1","0.96","0.64"},
			  				  {"1","0.9984","0.99328","1","0.992","0.896"},{"1","0.9984","0.99328","1","0.9984","0.9728"},
			  				  {"1","0.9984","0.99328","1","0.9984","0.9728"},{"1","0.9984","0.99328","1","0.9984","0.99328"},
			  				  {"1","0.8","0,1","0.9984","0.99328"},{"1","0.96","0.64","1","0.9984","0.99328"},
			  				  {"1","0.992","0.896","1","0.9984","0.99328"},{"1","0.9984","0.9728","1","0.9984","0.99328"},
			  				  {"1","0.9984","0.99328","1","0.9984","0.99328"},{"1","0.9984","0.99328","1","0.9984","0.99328"},
			  				  {"1","0.9984","0.99328","1","0.9984","0.99328"},{"1","0.9984","0.99328","1","0.9984","0.99328"},
			  				  {"1","0.9984","0.99328","1","0.9984","0.99328"},{"1","0.9984","0.99328","1","0.9984","0.99328"}};
	
	@BeforeEach
	void setUp() {
		workload = new WorkLoad(0, 0.8, 0.99, "Example1a.txt");
		warp = SystemFactory.create(workload, 16, ScheduleChoices.PRIORITY);
		tester = new ReliabilityAnalysis(program);
	}
	
	/**
	 * 
	 */
	@Test
	void testGetReliabilities() {
		
		Boolean status = true;
		
		ReliabilityTable actual = tester.getReliabilities();
		
		int x = 0;
		int y = 0;
		for(x = 0; x < expectedData.length;x++) {
			for(y = 0;y < expectedData[x].length;y++) {
				if(actual.get(x,y).toString() != expectedData[x][y]) {
					status = false;
				}
			}
		}
		assertTrue(status);
	}
	
	/**
	 * 
	 */
	// Jackie
	@Test
	void testVerifyReliabilities() {
		
		Boolean standing = true;
		Double e2e = 0.99;
		//String[] actual = tester.getFinalReliabilityRow();
		
		String[] lastRow = expectedData[expectedData.length];
		
		for (int i = 0; i < lastRow.length; i++) {

			if (i > e2e)
				standing = false;
		}
		
		assertTrue(standing);
	}
	
	/**
	 * 
	 */
	@Test
	void testBuildReliabilityTable() {
		fail("Not yet implemented");
	}
	
	
	/**
	 * Tests that carry forward reliabilities is setting the right values in the reliability table.
	 */
	// Jackie
	@Test
	void testCarryForwardReliabilities() {
		fail("Not yet implemented");
	}
	
	/**
	 * 
	 */
	@Test
	void testPrintRaTable() {
		fail("Not yet implemented");
	}
	
	/**
	 * Tests that set reliabilities is adding the correct data to the Reliability Table after it 
	 * has been built. 
	 */
	// Jackie
	@Test
	void testSetReliabilities() {
		fail("Not yet implemented");
	}
	
	/**
	 * 
	 */
	@Test
	void testSetInitialStateForReleasedFlows() {
		fail("Not yet implemented");
	}

	/** 
	 * Test for setting the reliability header row. Makes sure that the setter is setting
	 * the nodes in the proper order, and equal to the nodes actually in the flow. 
	 */
	// Jackie
	@Test
	void testSetReliabilityHeaderRow() {
		String[] expected = {"F0:A", "F0:B", "F0:C", "F1:C", "F1:B", "F1:A"};
		String[] actual = tester.getReliabilityHeaderRow();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}
	
	/**
	 * Stress test for setting the reliability header row. Makes sure that the setter is setting all of the nodes
	 * in the proper order and equal to the nodes in the flow."
	 */
	// Jackie
	@Test
	void testSetReliabilityHeaderRowStress() {
		WorkLoad workloadStress = new WorkLoad(0, 0.8, 0.99, "StressTest4.txt");
		WarpInterface warpStress = SystemFactory.create(workloadStress, 16, ScheduleChoices.PRIORITY);
		ReliabilityAnalysis testerStress = new ReliabilityAnalysis(program);
		
		String[] expected = {"F1:B", "F1:C", "F1:D", "F2:C", "F2:D", "F2:E", "F2:F", "F2:G", "F2:H", "F2:I", "F3:C", 
				"F3:D", "F3:E", "F3:J", "F3:K", "F3:L", "F4:A", "F4:B", "F4:C", "F4:D", "F4:E", "F4:J", "F4:K", "F4:L",
				"F5:A", "F5:B", "F5:C", "F5:D", "F5:E", "F6:B", "F6:C", "F6:D", "F7:A", "F7:B", "F7:C", "F7:D", "F7:E",
				"F8:C", "F8:D", "F8:E", "F8:F", "F8:G", "F8:H", "F8:I", "F9:A", "F9:B", "F9:C", "F9:D", "F9:E", "F9:J",
				"F9:K", "F9:L", "F10:C", "F10:D", "F10:E", "F10:J", "F10:K", "F10:L"};
		String[] actual = testerStress.getReliabilityHeaderRow();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}
}
