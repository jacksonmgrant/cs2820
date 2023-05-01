package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;
/**
 * Tests the class ReliabilityVisualization to make sure the 
 * reliability analysis methods are set up properly. 
 * In these tests, Jackie wrote the tests for testCreateColumnHeader(). 
 * Matt wrote the tests for testCreateHeader() and 
 * testCreateVisualizationData(). Jackson wrote the test for 
 * testCreateTitle(). 
 * 
 * @author Jackie Mills
 * @author Matt Boenish
 * @author Jackson Grant
 *
 */
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
		String[] expected = {"F0:A", "F0:B", "F0:C", "F1:C", "F1:B", "F1:A"};
		String[] actual = tester.createColumnHeader();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}

	@Test
	void testCreateVisualizationData() {
		
		Boolean status = true;
		
		String[][] expected = {{"1.0","0.8","0.0","1.0","0.0","0.0"},{"1.0","0.96","0.6400000000000001","1.0","0.0","0.0"},
				  			  {"1.0","0.992","0.896","1.0","0.0","0.0"},{"1.0","0.9984","0.9728000000000001","1.0","0.0","0.0"},{"1.0","0.9984","0.9932799999999999","1.0","0.0","0.0"},
				  			  {"1.0","0.9984","0.9932799999999999","1.0","0.8","0.0"},{"1.0","0.9984","0.9932799999999999","1.0","0.96","0.6400000000000001"},
				  			  {"1.0","0.9984","0.9932799999999999","1.0","0.992","0.896"},{"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9728000000000001"},
				  			  {"1.0","0.9984","0.9932799999999999","1.0","0.9984","0.9932799999999999"},{"1.0","0.99968","0.0","1.0","0.9984","0.9932799999999999"},
							  {"1.0", "0.9999359999999999", "0.799744", "1.0", "0.9984", "0.9932799999999999"},
							  {"1.0", "0.9999872", "0.9598976", "1.0", "0.9984", "0.9932799999999999"},{"1.0", "0.99999744", "0.99196928", "1.0", "0.9984", "0.9932799999999999"},
							  {"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},{"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},
							  {"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},{"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},
							  {"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"},{"1.0", "0.99999744", "0.9983918079999999", "1.0", "0.9984", "0.9932799999999999"}};
		
		String[][] actual = tester.createVisualizationData();
		
		if(expected.length != actual.length) {
			status = false;
		}
		int x = 0;
		int y = 0;
		for(x = 0;x < expected.length;x++) {
			if(actual[x].length != expected[x].length) {
				status = false;
			}
			for(y = 0;y < expected[x].length;y++) {
				if(expected[x][y].compareTo(actual[x][y]) != 0) {
					status = false;
				}
			}
		}
		assertTrue(status);
	}

	@Test
	void testCreateTitle() {
		String expected = "Reliability Analysis for graph Example1A\n";
		String actual = tester.createTitle();
		
		assertEquals(expected, actual);
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
