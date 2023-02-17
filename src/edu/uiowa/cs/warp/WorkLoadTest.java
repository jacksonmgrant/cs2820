package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class WorkLoadTest {
	private WorkLoad stressTestWorkLoad;
	private WorkLoad exampleThreeWorkLoad;
	private WorkLoad testingWorkLoad;
	
	
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
		String stressTest = "StressTest.txt";
		stressTestWorkLoad = new WorkLoad(0.9, 0.99, stressTest);
		
		String exampleThree = "Example3.txt";
		exampleThreeWorkLoad = new WorkLoad(0.9, 0.99, exampleThree);
		
		//I didn't want to mess with your code without you knowing, so 
		//I left testingWorkLoad as is. However, it's the same as 
		//stressTestWorkLoad so we should only keep one to avoid redundancy
		//and confusion, or change testingWorkLoad to run a different file
		// if there's another case we want to test. --Jackson
		String testingFile = "StressTest.txt";
		testingWorkLoad = new WorkLoad(0.9, 0.99, testingFile);
		
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
	void testAddNodeToFlow() { // ask
		fail("Not yet implemented");
		

	}

	@Test
	void testGetFlowPriority() {
		Flow originalFlow = new Flow("Test flow to be added", 0, 0);
		
		//Stress Test
		stressTestWorkLoad.addFlow(originalFlow.getName());
		int expectedPriority = 15;
		int actualPriority = stressTestWorkLoad.getFlowPriority(originalFlow.getName());
		assertEquals(expectedPriority, actualPriority);
		
		//Example 3
		exampleThreeWorkLoad.addFlow(originalFlow.getName());
		expectedPriority = 6;
		actualPriority = exampleThreeWorkLoad.getFlowPriority(originalFlow.getName());
		assertEquals(expectedPriority, actualPriority);
	}

	@Test
	void testSetFlowPriority() { //ask
		int setFP = testingWorkLoad.getFlowPriority("F1");
		testingWorkLoad.setFlowPriority("F1", 100);
		int actualFP = testingWorkLoad.getFlowPriority("F1");
		assertEquals(100, actualFP);
		assertNotEquals(setFP, actualFP);
		
	}

	@Test
	void testSetFlowDeadline() {
		fail("Not yet implemented");
	}

	@Test
	void testGetFlowDeadline() { 
		//Stress Test:
		Integer expected = 100;
		Integer actual = stressTestWorkLoad.getFlowDeadline("F9");
		assertEquals(expected, actual);
		
		//Example 3:
		expected = 100;
		actual = exampleThreeWorkLoad.getFlowDeadline("F0");
		assertEquals(expected, actual);
	}

	@Test
	void testGetFlowTxAttemptsPerLink() { //ask
		
		fail("Not yet implemented");
	}

	@Test
	void testSetFlowsInRMorder() {
		//Stress Test:
		stressTestWorkLoad.setFlowsInRMorder();
		String expected = "[F1, AF1, F2, AF2, F3, F4, AF4, F5, AF5, F6, F7, F8, F9, F10, AF10]";
		String actual = stressTestWorkLoad.getFlowNamesInPriorityOrder().toString();
		assertEquals(expected, actual);
		
		//Example 3:
		exampleThreeWorkLoad.setFlowsInRMorder();
		expected = "[F0, F1, F2, F3, F4, F5]";
		actual = exampleThreeWorkLoad.getFlowNamesInPriorityOrder().toString();
		assertEquals(expected, actual);
		
		//Old code as of 2/17/23. Delete if new code is acceptable, we are checking if 
		//new code is acceptable on Mon 2/20/23.
		/*
		//Expected:
		//Extract all flows from the testing WorkLoad
		Collection<Flow> temp = stressTestWorkLoad.getFlows().values();
		ArrayList<Flow> flows = new ArrayList<Flow>(0);
		for(Flow flow : temp)
			flows.add(flow);
		
		//Bubblesort the flows according to period first and priority second
		for(int i = 0; i < flows.size()-2; i++) {
			for(int j = 0; j < flows.size()-i-1; j++) {
				if(flows.get(j).getPeriod() == flows.get(j+1).getPeriod()) {
					if(flows.get(j).getPriority() > flows.get(j+1).getPriority()) {
						Collections.swap(flows, j, j+1);
					}
				}else if(flows.get(j).getPeriod() > flows.get(j+1).getPeriod()) {
					Collections.swap(flows, j, j+1);
				}
			}
		}
		
		//Generate expected values
		ArrayList<String> expected = new ArrayList<String>(0);
		for(Flow flow : flows) {
			expected.add(flow.getName());
		}
		
		//Actual:
		stressTestWorkLoad.setFlowsInRMorder();
		ArrayList<String> actual = stressTestWorkLoad.getFlowNamesInPriorityOrder();
		System.out.println(actual.toString());
		assertEquals(expected, actual);
		*/
	}

	@Test
	void testGetNodeNamesOrderedAlphabetically() {
		String[] expected = {"A","B","C","D","E","F","G","H","I",
				"J","K","L","M","N","O","P","Q","R","S","T","U","V","W","Y"};
		String[] actual = testingWorkLoad.getNodeNamesOrderedAlphabetically();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
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
		
		//Old code as of 2/17/23. Delete if new code is acceptable, we are checking if 
		//new code is acceptable on Mon 2/20/23.
		/*
//		Doesn't work with example 3. It uses colons. Chars in flow name 
//		need to be between 48 and 57, 65 and 90, or 97 and 122, all inclusive.
		
		System.out.println((int)' ');
		WorkLoadDescription getFlows = new WorkLoadDescription(testingFile);
		Description flows = getFlows.visualization();
		flows.remove(0);
		flows.remove(flows.size()-1);
		String[] expected = new String[flows.size()];
		for(int i = 0; i < expected.length; i++) {
			String currentFlow = flows.get(i);
			expected[i] = currentFlow.substring(0, currentFlow.indexOf(' '));
			
//			//Need to remove any non-alpha or non-numeric characters
//			for(int j = 0; j < expected[i].length(); j++) {
//				char c = expected[i].charAt(j);
//				if(!((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122))) {
//					expected[i] = expected[i].substring(0,expected[i].indexOf(c)) +
//							expected[i].substring(expected[i].indexOf(c+1));
//				}
//			}
		}
		String[] actual = stressTestWorkLoad.getFlowNames();
		System.out.println(Arrays.toString(expected));
		System.out.println(Arrays.toString(actual));
		assertEquals(Arrays.toString(expected),Arrays.toString(actual));
		*/
	}

	@Test
	void testGetNodeIndex() { //ask
		fail("Not yet implemented");
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
	void testGetHyperPeriod() { //ask
		
		var expected = 300;
		var actual = testingWorkLoad.getHyperPeriod();
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
		var maxFL = 8;
		assertEquals(maxFL, testingWorkLoad.maxFlowLength());
		
//		String testingFile2 = "Example.txt";
//		WorkLoad tester2 = new WorkLoad(0.9, 0.99, testingFile2);
//		var maxFL2 = 3;
//		assertEquals(maxFL2, tester2.maxFlowLength());
				
	}

}
