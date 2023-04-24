package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

class ReliabilityAnalysisTest {

	private WorkLoad workload;
	private Program program;
	private WarpInterface warp;
	private ReliabilityVisualization tester;
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
		tester = new ReliabilityVisualization(warp);
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void testGetReliabilities() {
		fail("Not yet implemented");
	}
	
	@Test
	void testVerifyReliabilities() {
		fail("Not yet implemented");
	}

}
