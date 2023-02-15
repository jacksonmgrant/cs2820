package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

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
		fail("Not yet implemented");
	}

	@Test
	void testGetFlowPriorityStringString() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	@Test
	void testGetNumTxAttemptsPerLink() {
		fail("Not yet implemented");
	}

	@Test
	void testMaxFlowLength() {
		fail("Not yet implemented");
	}

}
