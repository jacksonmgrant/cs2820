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
		workload = new WorkLoad(0, 0.9, 0.9, "Example1a.txt");
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
		fail("Not yet implemented");
	}

	@Test
	void testCreateColumnHeader() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateVisualizationData() {
		fail("Not yet implemented");
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
