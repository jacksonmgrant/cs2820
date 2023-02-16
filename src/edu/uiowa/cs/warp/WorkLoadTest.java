package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;


class WorkLoadTest {

	@Test
	void testAddFlow() {
		//Can add to test to check priority and index changed properly??
		String testingFile = "Example4.txt";
		WorkLoad tester = new WorkLoad(0.9, 0.99, testingFile);
		Flow originalFlow = new Flow("Test flow to be added", 0, 0);
		tester.addFlow(originalFlow.getName());
		FlowMap allFlowsInTester = tester.getFlows();
		Flow addedFlow = allFlowsInTester.get(originalFlow.getName());
		assertEquals(originalFlow.getName(), addedFlow.getName());
	}

	@Test
	void testAddNodeToFlow() {
		String testingFile = "Example4.txt";
		WorkLoad tester = new WorkLoad(0.9, 0.99, testingFile);
		tester.addNodeToFlow("F0", "E");
				
	}

	@Test
	void testGetFlowPriority() {
		String testingFile = "Example4.txt";
		WorkLoad tester = new WorkLoad(0.9, 0.99, testingFile);
		Flow originalFlow = new Flow("Test flow to be added", 0, 0);
		tester.addFlow(originalFlow.getName());
		int expectedPriority = tester.getFlowNames().length -1;
		int actualPriority = tester.getFlowPriority(originalFlow.getName());
		assertEquals(expectedPriority, actualPriority);
	}

	@Test
	void testSetFlowPriority() {
		fail("Not yet implemented");
	}

	@Test
	void testSetFlowDeadline() {
		fail("Not yet implemented");
	}

	@Test
	void testGetFlowDeadline() {
		fail("Not yet implemented");
	}

	@Test
	void testGetFlowTxAttemptsPerLink() {
		fail("Not yet implemented");
	}

	@Test
	void testSetFlowsInRMorder() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNodeNamesOrderedAlphabetically() {
		
		String testingFile = "Example4.txt";
		WorkLoad tester = new WorkLoad(0.9, 0.99, testingFile);
		String[] expected = {"A","B","C","D"};
		String[] actual = tester.getNodeNamesOrderedAlphabetically();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}

	@Test
	void testGetFlowNames() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNodeIndex() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNodesInFlow() {
		fail("Not yet implemented");
	}

	@Test
	void testGetHyperPeriod() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTotalTxAttemptsInFlow() {
		String testingFile = "StressTest.txt";
		WorkLoadDescription getFlowName = new WorkLoadDescription(testingFile);
		String flow = getFlowName.visualization().get(4);
		String flowName = flow.substring(0, flow.indexOf(' '));
		//System.out.println(flow);
		
		//Expected:
		
		//Actual:
		WorkLoad tester = new WorkLoad(0.9, 0.99, testingFile);
		Integer actual = tester.getTotalTxAttemptsInFlow(flowName);
		//System.out.println(actual);
		fail("Not yet implemented");
	}

	@Test
	void testGetNumTxAttemptsPerLink() {
		fail("Not yet implemented");
	}

	@Test
	void testMaxFlowLength() {

		String testingFile = "Example4.txt";
		WorkLoad tester = new WorkLoad(0.9, 0.99, testingFile);
		var maxFL = 4;
		assertEquals(maxFL, tester.maxFlowLength());
		
//		String testingFile2 = "Example.txt";
//		WorkLoad tester2 = new WorkLoad(0.9, 0.99, testingFile2);
//		var maxFL2 = 3;
//		assertEquals(maxFL2, tester2.maxFlowLength());
				
	}

}
