package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

/** 
 * Tests the class ReliabilityAnalysis to make sure the methods are properly set up. In these tests, Jackie wrote the
 * testVerifyReliabilities() and Matt wrote the test for testGetReliabilities().
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
	
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
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
	
	@Test
	void testVerifyReliabilities() {
		fail("Not yet implemented");
	}
	
	@Test
	void testBuildReliabilityTable() {
		fail("Not yet implemented");
	}
	
	@Test
	void testCarryForwardReliabilities() {
		fail("Not yet implemented");
	}
	
	@Test
	void testPrintRaTable() {
		fail("Not yet implemented");
	}
	
	@Test
	void testSetReliabilities() {
		fail("Not yet implemented");
	}
	
	@Test
	void testSetInitialStateForReleasedFlows() {
		fail("Not yet implemented");
	}

	@Test
	void testSetReliabilityHeaderRow() {
		fail("Not yet implemented");
	}
}
