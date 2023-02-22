package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class WorkLoadTest {
	private WorkLoad stressTestWorkLoad;
	private WorkLoad exampleThreeWorkLoad;
	//private WorkLoad testingWorkLoad;
	
	
	@BeforeEach
	public void setUp() {
		//I chose StressTest for a high volume test case, and Example3 because 
		//it and Example2 have different formatting than all the other examples.
		//SeeSpray or Test1 could also be added for tests involving the flow names
		//because the names are formatted differently in those files. As another
		//option, we could create our own test file where we can experiment with
		//a lot of different cases for highly rigorous testing (I'd be happy to
		//implement this). Let me know what you think, and feel free to 
		//make any changes. --Jackson
		
		//Both files seem like a good fit to run tests on. Because of the different
		//formatting the parameters being looked at changes too, so having distinct files 
		// is neat. For now, all tests are passing.
		//If these testing methods are approved of, then sure we can make our own 
		//file to work with, it would be good practice. --Nalini
		
		
		String stressTest = "StressTest.txt";
		stressTestWorkLoad = new WorkLoad(0.9, 0.99, stressTest);
		
		String exampleThree = "Example3.txt";
		exampleThreeWorkLoad = new WorkLoad(0.9, 0.99, exampleThree);		
	}

	@Test
	void testAddFlow() {
		//Can add to test to check priority and index changed properly??
		Flow originalFlow = new Flow("Test flow to be added", 0, 0);
		stressTestWorkLoad.addFlow(originalFlow.getName());
		FlowMap allFlowsInstressTestWorkLoad = stressTestWorkLoad.getFlows();
		Flow addedFlow = allFlowsInstressTestWorkLoad.get(originalFlow.getName());
		assertEquals(originalFlow.getName(), addedFlow.getName());
	}

	@Test
	void testAddNodeToFlow() { 
		 
		//Stress Test
		var initial = stressTestWorkLoad.getNodesInFlow("F5");
		stressTestWorkLoad.addNodeToFlow("F5", "F");
		var updated = stressTestWorkLoad.getNodesInFlow("F5");
	
		assertEquals("F", updated[updated.length-1]);
		assertNotEquals(initial, updated);
		
		
		//Example 3
//		var start = exampleThreeWorkLoad.getNodesInFlow("F0");
//		exampleThreeWorkLoad.addNodeToFlow("F0", "D");
//		var end = exampleThreeWorkLoad.getNodesInFlow("F0");
//	
//		assertEquals("D", end[end.length-1]);
//		assertNotEquals(start, end);
	
	}

	@Test
	void testGetFlowPriority() {
		Flow originalFlow = new Flow("Test flow to be added", 0, 0);
		
		//Using the stress test file
		stressTestWorkLoad.addFlow(originalFlow.getName());
		int expectedPriority = 15;
		int actualPriority = stressTestWorkLoad.getFlowPriority(originalFlow.getName());
		assertEquals(expectedPriority, actualPriority);
	}

	@Test
	void testSetFlowPriority() { 
		//Stress Test
//		int setFP = stressTestWorkLoad.getFlowPriority("F1");
//		stressTestWorkLoad.setFlowPriority("F1", 100);
//		int actualFP = stressTestWorkLoad.getFlowPriority("F1");
//		assertEquals(100, actualFP);
//		assertNotEquals(setFP, actualFP);
		
		//Example 3
		int setThreeFP = exampleThreeWorkLoad.getFlowPriority("F3");
		exampleThreeWorkLoad.setFlowPriority("F3", 30);
		int actualThreeFP = exampleThreeWorkLoad.getFlowPriority("F3");
		assertEquals(30, actualThreeFP);
		assertNotEquals(setThreeFP, actualThreeFP);
		
	}

	@Test
	void testSetFlowDeadline() { 
		//Stress Test
		int setFD = stressTestWorkLoad.getFlowDeadline("F3");
		stressTestWorkLoad.setFlowDeadline("F3", 100);
		int actualFD = stressTestWorkLoad.getFlowDeadline("F3");
		assertEquals(100, actualFD);
		assertNotEquals(setFD, actualFD);
		
		//Example 3
		int setThreeFD = exampleThreeWorkLoad.getFlowDeadline("F0");
		exampleThreeWorkLoad.setFlowDeadline("F0", 20);
		int actualThreeFD = exampleThreeWorkLoad.getFlowDeadline("F0");
		assertEquals(20, actualThreeFD);
		assertNotEquals(setThreeFD, actualThreeFD);
		
	}

	@Test
	void testGetFlowDeadlineStressTest() { 
		//Using the Stress Test to test when a parameter is passed:
		Integer expected = 50;
		Integer actual = stressTestWorkLoad.getFlowDeadline("F3");
		assertEquals(expected, actual);
		
		
	}
	
	@Test
	void testGetFlowDeadlineDefault() {
		//Using Example 3 to test that it works with the default values:
		Integer expected = 100;
		Integer actual = exampleThreeWorkLoad.getFlowDeadline("F0");
		assertEquals(expected, actual);
	}

	@Test
	void testGetFlowTxAttemptsPerLink() { 
		
		//Stress Test:
		Integer expected = 3;
		Integer actual = stressTestWorkLoad.getFlowTxAttemptsPerLink("F1");
		assertEquals(expected, actual);
		
		int expectedAF4 = 3;
		int actualAF4 = stressTestWorkLoad.getFlowTxAttemptsPerLink("AF4");
		assertEquals(expectedAF4, actualAF4);
		
		//Example 3:
		expected = 3;
		actual = exampleThreeWorkLoad.getFlowTxAttemptsPerLink("F1");
		assertEquals(expected, actual);
	
		int expectedF5 = 3;
		int actualF5 = exampleThreeWorkLoad.getFlowTxAttemptsPerLink("F5");
		assertEquals(expectedF5, actualF5);
		
	}

	@Test
	void testSetFlowsInRMorderStressTest() {
		//Test using Stress Test for input parameters and volume:
		stressTestWorkLoad.setFlowsInRMorder();
		String expected = "[F1, AF1, F2, AF2, F3, F4, AF4, F5, AF5, F6, F7, F8, F9, F10, AF10]";
		String actual = stressTestWorkLoad.getFlowNamesInPriorityOrder().toString();
		assertEquals(expected, actual);
	}
	
	@Test
	void testSetFlowsInRMOrderStressTest() {
		//Test using Example 3 for default parameters and different formatting:
		exampleThreeWorkLoad.setFlowsInRMorder();
		String expected = "[F0, F1, F2, F3, F4, F5]";
		String actual = exampleThreeWorkLoad.getFlowNamesInPriorityOrder().toString();
		assertEquals(expected, actual);
	}

	@Test
	void testGetNodeNamesOrderedAlphabetically() {
		
		//Stress Test
		String[] expected = {"A","B","C","D","E","F","G","H","I",
				"J","K","L","M","N","O","P","Q","R","S","T","U","V","W","Y"};
		String[] actual = stressTestWorkLoad.getNodeNamesOrderedAlphabetically();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
		
		//Example 3
		String[] expectedThree = {"A","B","C","D","F","G","I",
				"P","R","V","X"};
		String[] actualThree = exampleThreeWorkLoad.getNodeNamesOrderedAlphabetically();
		for (int i = 0; i < expectedThree.length; i++) {
			assertEquals(expectedThree[i], actualThree[i]);
		}
	}

	@Test
	void testGetFlowNames() {
		//StressTest:
		String expected = "[F1, F5, F2, F4, F3, F6, F7, F8, F9, F10, AF1, AF5, AF2, AF4, AF10]";
		String[] actual = stressTestWorkLoad.getFlowNames();
		assertEquals(expected, Arrays.toString(actual));
		
		//Example3:
		expected = "[F0, F1, F2, F3, F4, F5]";
		actual = exampleThreeWorkLoad.getFlowNames();
		assertEquals(expected, Arrays.toString(actual));
	}

	@Test
	void testGetNodeIndex() { // ?
		
		//Stress Test
		var expected = 0;
		var actual = stressTestWorkLoad.getNodeIndex("F1");
		assertEquals(expected,actual);
		
		var expectedL = 11;
		var actualL = stressTestWorkLoad.getNodeIndex("L");
		assertEquals(expectedL,actualL);
		
		//Example 3
		expected = 0;
		actual = exampleThreeWorkLoad.getNodeIndex("F0");
		assertEquals(expected,actual);
		
		int expectedV = 9;
		int actualV = exampleThreeWorkLoad.getNodeIndex("V");
		assertEquals(expectedV,actualV);
		
	}

	@Test
	void testGetNodesInFlow() {
		//Stress Test:
		String[] actual = stressTestWorkLoad.getNodesInFlow("F9");
		String expected = "[A, B, C, D, E, J, K, L]";
		assertEquals(expected, (Arrays.toString(actual)));
		
		//Example 3:
		actual = exampleThreeWorkLoad.getNodesInFlow("F0");
		expected = "[A, B, C]";
		assertEquals(expected, (Arrays.toString(actual)));
	}

	@Test
	void testGetHyperPeriodStressTest() {
		
		//Using the Stress Test file to see if the value is calculated correctly
		var expected = 300;
		var actual = stressTestWorkLoad.getHyperPeriod();
		assertEquals(expected,actual);
	}
	@Test
	void testGetHyperPeriodDefault() {
		
		//Using the Example 3 file to see if the default is set correctly
		var expected = 100;
		var actual = exampleThreeWorkLoad.getHyperPeriod();
		assertEquals(expected,actual);
		
	}

	@Test
	void testGetTotalTxAttemptsInFlow() {
		//Stress Test:
		Integer expected = 11;
		Integer actual = stressTestWorkLoad.getTotalTxAttemptsInFlow("F9");
		assertEquals(expected, actual);
		
		//Example 3:
		expected = 4;
		actual = exampleThreeWorkLoad.getTotalTxAttemptsInFlow("F0");
		assertEquals(expected, actual);
	}

	@Test
	void testGetNumTxAttemptsPerLink() {
		//Stress Test:
		String expected = "[3, 4, 5, 6, 6, 6, 5, 0]";
		Integer[] actual = stressTestWorkLoad.getNumTxAttemptsPerLink("F9");
		assertEquals(expected, Arrays.toString(actual));
		
		//Example 3:
		expected = "[3, 3, 0]";
		actual = exampleThreeWorkLoad.getNumTxAttemptsPerLink("F0");
		assertEquals(expected, Arrays.toString(actual));
	}

	@Test
	void testMaxFlowLength() {
		
		//Stress Test:
		var maxFL = 8;
		assertEquals(maxFL, stressTestWorkLoad.maxFlowLength());
		
		//Example 3:
		int maxFL3 = 3;
		assertEquals(maxFL3, exampleThreeWorkLoad.maxFlowLength());
						
	}

}
