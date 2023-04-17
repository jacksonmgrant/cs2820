package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

class ReliabilityVisualizationTest {

	private WorkLoad workload;
	private Program program;
	private WarpInterface warp;
	private ReliabilityVisualization tester;
	
	@BeforeEach
	void setUp() {
		workload = new WorkLoad(0, 0.8, 0.99, "Example1a.txt");
		warp = SystemFactory.create(workload, 16, ScheduleChoices.PRIORITY);
		tester = new ReliabilityVisualization(warp);
	}
	
	//This method may or may not be overridden from VisualizationObject. We should
	//still test it, but write the other tests first, then write it to the
	//VisualizationObject method if we haven't figured it out yet.
	@Test
	void testVisualization() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateHeader() {
		Description expected = new Description();

		expected.add(tester.createTitle());
	    
	    Program program = warp.toProgram();
	    expected.add(String.format("Scheduler Name: %s\n", program.getSchedulerName()));
	    expected.add(String.format("M: %s\n", String.valueOf(program.getMinPacketReceptionRate())));
	    expected.add(String.format("E2E: %s\n", String.valueOf(program.getE2e())));
	    expected.add(String.format("nChannels: %d\n", program.getNumChannels()));
	    
	    Description actual = new Description();
	    
	    actual = tester.createHeader();
	    
	    assertEquals(expected, actual);
	}

	@Test
	void testCreateColumnHeader() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateVisualizationData() {
		
		String[][] expected = {{"1","0.8","0","1","0","0"},{"1","0.96","0.64","1","0","0"},
							  {"1","0.992","0.896","1","0","0"},{"1","0.9984","0.9728","1","0","0"},{"1","0.9984","0.99328","1","0","0"},
							  {"1","0.9984","0.99328","1","0.8","0"},{"1","0.9984","0.99328","1","0.96","0.64"},
							  {"1","0.9984","0.99328","1","0.992","0.896"},{"1","0.9984","0.99328","1","0.9984","0.9728"},
							  {"1","0.9984","0.99328","1","0.9984","0.9728"},{"1","0.9984","0.99328","1","0.9984","0.99328"},
							  {"1","0.8","0,1","0.9984","0.99328"},{"1","0.96","0.64","1","0.9984","0.99328"},
							  {"1","0.992","0.896","1","0.9984","0.99328"},{"1","0.9984","0.9728","1","0.9984","0.99328"},
							  {"1","0.9984","0.99328","1","0.9984","0.99328"},{"1","0.9984","0.99328","1","0.9984","0.99328"},
							  {"1","0.9984","0.99328","1","0.9984","0.99328"},{"1","0.9984","0.99328","1","0.9984","0.99328"},
							  {"1","0.9984","0.99328","1","0.9984","0.99328"},{"1","0.9984","0.99328","1","0.9984","0.99328"}};
		
		String[][] actual = tester.createVisualizationData();
		
		assertEquals(expected, actual);
	}

	@Test
	void testCreateTitle() {
		String expected = "Reliability Analysis for graph Example1A";
		String actual = tester.createTitle();
		
		assertEquals(expected, actual);
	}

	//This one is implemented in VisualizationObject, may be an easy way to get
	//file data but wait to implement until we check if we need it.
	@Test
	void testCreateFile() {
		fail("Not yet implemented");
	}
	
	/**
	 * Verifies that the format of the output file matches the required format.
	 */
	@Test
	void testFileFormat() {
		fail("Not yet implemented");
	}
	
	/**
	 * Verifies that the reliabilities in the output file are correct.
	 */
	@Test
	void checkMath() {
		fail("Not yet implemented");
	}

}
