package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

/** 
 * Tests the class ReliabilityAnalysis using the files Example1a.txt and some from StressTest4.txt to make sure the 
 * methods are properly set up. In these tests, Jackie wrote the testVerifyReliabilities(), 
 * testCarryForwardReliabilities(), testSetReliabilities(), testSetReliabilityHeaderRow().
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
	private ReliabilityTable rTable;
	/*String[][] expectedData = {{"1.0","0.8","0.0","1.0","0.0","0.0"},{"1.0","0.96","0.6400000000000001","1.0","0.0","0.0"},
			  {"1.0","0.992","0.896","1.0","0.0","0.0"},{"1.0","0.9984","0.9728000000000001","1.0","0.0","0.0"},{"1.0","0.9984","0.9932799999999999","1.0","0.0","0.0"},
			  {"1.0","0.9984","0.9932799999999999","1.0","0.8","0.0"},{"1.0","0.9984","0.9932799999999999","1.0","0.96","0.6400000000000001"},
			  {"1.0","0.9984","0.9932799999999999","1.0","0.992","0.896"},{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9728000000000001"},
			  {"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9932799999999999"},{"1.0","0.99968","0.0","1.0","0.9984","0.9932799999999999"},
			  {"1.0", "0.9999359999999999", "0.799744", "1.0", "0.9984", "0.9932799999999999"},
			  {"1.0", "0.9999872", "0.9598976", "1.0", "0.9984", "0.9932799999999999"},{"1.0", "0.99999744", "0.99196928", "1.0", "0.9984", "0.9932799999999999"},
			  {"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},{"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},
			  {"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},{"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},
			  {"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},{"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"}};*/
	
	String[][] expectedData = {{"1.0","0.8","0.0","1.0","0.0","0.0"},
		{"1.0","0.96","0.6400000000000001","1.0","0.0","0.0"},
		{"1.0","0.992","0.896","1.0","0.0","0.0"},
		{"1.0","0.9984","0.9728000000000001","1.0","0.0","0.0"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.0","0.0"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.8","0.0"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.96","0.6400000000000001"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.992","0.896"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9728000000000001"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.8","0.0","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.96","0.6400000000000001","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.992","0.896","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.9984","0.9728000000000001","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9932799999999999"},
		{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9932799999999999"}};
	
	@BeforeEach
	void setUp() {
		workload = new WorkLoad(0, 0.8, 0.99, "Example1a.txt");
		warp = SystemFactory.create(workload, 16, ScheduleChoices.PRIORITY);
		program = warp.toProgram();
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
				System.out.print("  "+ status +" (" + x + "," + y + ") " + actual.get(x, y).toString() + expectedData[x][y]);
				if(actual.get(x, y).toString().compareTo(expectedData[x][y]) != 0) {
					status = false;
				}
			}
		}
		assertTrue(status);
	}
	
	/**
	 * Asserts that the end to end reliability is met at each point where the period changes. This test 
	 * makes sure that verify reliabilities returns true if they're all met.
	 */
	@Test
	void testVerifyReliabilities() {
		
		boolean standing = true;
		Double e2e = 0.99;
		boolean actual = tester.verifyReliabilities();
		
		double first = Double.parseDouble(expectedData[10][2]);
		double second = Double.parseDouble(expectedData[19][2]);
		double third = Double.parseDouble(expectedData[19][5]);
		
		if ((first < e2e) || (second < e2e) || (third < e2e)) {
			standing = false; }
		
		//String[] lastRow = expectedData[expectedData.length-1];
		//for(int i = 0; i < lastRow.length; i++) {
		//	if(i < e2e) {
		//		standing = false;
		//	}
		//}
		
		assertEquals(standing, actual);
	}
	
	@Test
	void testVerifyReliabilitiesStress() {
		WorkLoad workloadStress = new WorkLoad(0, 0.9, 0.99, "StressTest4.txt");
		WarpInterface warpStress = SystemFactory.create(workloadStress, 16, ScheduleChoices.PRIORITY);
		Program program = warpStress.toProgram();
		ReliabilityAnalysis testerStress = new ReliabilityAnalysis(program);
		
		boolean standing = true;
		Double e2e = 0.99;
		boolean actual = testerStress.verifyReliabilities();
		
		ReliabilityTable stressTable = testerStress.getReliabilities();
		if (stressTable.get(57, 99) > e2e) 
			standing = false;
		
		assertFalse(actual);
		//assertEquals(standing, actual);
		
	}
	
	/**
	 * 
	 */
	@Test
	void testBuildReliabilityTable() {
		Boolean status = true;
		
		ReliabilityTable actual = tester.buildReliabilityTable();
		
		int x = 0;
		int y = 0;
		for(x = 0; x < expectedData.length;x++) {
			for(y = 0;y < expectedData[x].length;y++) {
				if(!actual.get(x,y).toString().equals(expectedData[x][y])) {
					//System.out.println(" Actual: " + actual.get(x, y) + " Expected: " +expectedData[x][y] + " " + x + " " +y);
					status = false;
				}
			}
		}
		assertTrue(status);
	}
	
	
	/**
	 * Tests that carry forward reliabilities is setting the right values in the reliability table.
	 */
	@Test
	void testCarryForwardReliabilities() {
		rTable = tester.getReliabilities();
		ReliabilityTable actual = tester.carryForwardReliabilities(0, rTable);
		
		for(int i = 0; i < expectedData.length; i++) {
			for(int j = 0; j < expectedData[i].length; j++) {
				assertEquals((actual.get(i,j).toString()), (expectedData[i][j]));
			}
		}
	}
	
	/**
	 * 
	 */
	@Test
	void testPrintRaTable() {
		
		ReliabilityTable table = tester.getReliabilities();
		String[] actual = tester.printRATable(table);
		String[] expected = new String[21];
		
		String cRow = "";
		int x = 0;
		int y = 0;
		for(x = 0;x < expectedData.length;x++) {
			cRow = "";
			for(y = 0;y < expectedData[x].length;y++) {
				cRow = cRow + table.get(x, y)+"\t";
			}
			expected[x+1] = cRow;
		}
		String[] headerRow = tester.getReliabilityHeaderRow();
		expected[0] = "";
		int z = 0;
		for(z = 0;z < headerRow.length;z++) {
			expected[0] = expected[0] + headerRow[z]+"\t";
		}
		Boolean status = true;
		int i = 0;
		for(i = 0;i < expected.length;i++) {
			if(!actual[i].equals(expected[i])) {
				status = false;
			}
		}
		assertTrue(status);
	}
	
	/**
	 * Tests that set reliabilities is adding the correct data to the Reliability Table after it 
	 * has been built. 
	 */
	@Test
	void testSetReliabilities() {
		ReliabilityTable actual = tester.getReliabilities();
		
		for(int i = 0; i < expectedData.length; i++) {
			for(int j = 0; j < expectedData[i].length; j++) {
				assertEquals((actual.get(i,j).toString()), (expectedData[i][j]));
			}
		}
	}
	
	/**
	 * 
	 */
	@Test
	void testSetInitialStateForReleasedFlows() {
		
		NodeMap nodeMap = tester.buildNodeMap(workload);
		ReliabilityTable actual = new ReliabilityTable(20, 6);
		actual = tester.setInitialStateForReleasedFlows(nodeMap, actual);
		String[][] expected = {{"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"},
							   {"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"},
							   {"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"},
							   {"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"},
							   {"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"},
							   {"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"},
							   {"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"},
							   {"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"},
							   {"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"},
							   {"1.0","0.0","0.0","1.0","0.0","0.0"},{"1.0","0.0","0.0","1.0","0.0","0.0"}};
		Boolean status = true;
		
		int x = 0;
		int y = 0;
		for(x = 0; x < expected.length;x++) {
			for(y = 0;y < expected[x].length;y++) {
				if(!actual.get(x, y).toString().equals(expected[x][y])) {
					status = false;
				}
			}
		}
		assertTrue(status);
	}

	/** 
	 * Test for setting the reliability header row. Makes sure that the setter is setting
	 * the nodes in the proper order, and equal to the nodes actually in the flow. 
	 */
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
	@Test
	void testSetReliabilityHeaderRowStress() {
		WorkLoad workloadStress = new WorkLoad(0, 0.9, 0.99, "StressTest4.txt");
		WarpInterface warpStress = SystemFactory.create(workloadStress, 16, ScheduleChoices.PRIORITY);
		Program program = warpStress.toProgram();
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
