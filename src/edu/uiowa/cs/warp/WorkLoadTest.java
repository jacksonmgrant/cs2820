package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class WorkLoadTest {
	
	private String testingFile;
	private WorkLoad testingWorkLoad;
	
	@BeforeEach
	public void setUp() {
		testingFile = "StressTest.txt";
		testingWorkLoad = new WorkLoad(0.9, 0.99, testingFile);
	}

	@Test
	void testAddFlow() {
		//Can add to test to check priority and index changed properly??
		Flow originalFlow = new Flow("Test flow to be added", 0, 0);
		testingWorkLoad.addFlow(originalFlow.getName());
		FlowMap allFlowsIntestingWorkLoad = testingWorkLoad.getFlows();
		Flow addedFlow = allFlowsIntestingWorkLoad.get(originalFlow.getName());
		assertEquals(originalFlow.getName(), addedFlow.getName());
	}

	@Test
	void testAddNodeToFlow() {
		//testingWorkLoad.addNodeToFlow("F0", "E");
		
		fail("Not yet implemented");
	}

	@Test
	void testGetFlowPriority() {
		Flow originalFlow = new Flow("Test flow to be added", 0, 0);
		testingWorkLoad.addFlow(originalFlow.getName());
		int expectedPriority = testingWorkLoad.getFlowNames().length -1;
		int actualPriority = testingWorkLoad.getFlowPriority(originalFlow.getName());
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
		//Expected:
		/*Extract all flows from the testing WorkLoad*/
		Collection<Flow> temp = testingWorkLoad.getFlows().values();
		ArrayList<Flow> flows = new ArrayList<Flow>(0);
		for(Flow flow : temp)
			flows.add(flow);
		
		/*Bubblesort the flows according to period first and priority second*/
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
		
		/*Generate expected values*/
		ArrayList<String> expected = new ArrayList<String>(0);
		for(Flow flow : flows) {
			expected.add(flow.getName());
		}
		
		//Actual:
		testingWorkLoad.setFlowsInRMorder();
		ArrayList<String> actual = testingWorkLoad.getFlowNamesInPriorityOrder();
		assertEquals(expected, actual);
	}

	@Test
	void testGetNodeNamesOrderedAlphabetically() {
		
		String[] expected = {"A","B","C","D"};
		String[] actual = testingWorkLoad.getNodeNamesOrderedAlphabetically();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}

	@Test
	void testGetFlowNames() {
		/*
		 * Doesn't work with example 3. It uses colons. Chars in flow name 
		 * need to be between 48 and 57, 65 and 90, or 97 and 122, all inclusive.
		 */
		System.out.println((int)' ');
		WorkLoadDescription getFlows = new WorkLoadDescription(testingFile);
		Description flows = getFlows.visualization();
		flows.remove(0);
		flows.remove(flows.size()-1);
		String[] expected = new String[flows.size()];
		for(int i = 0; i < expected.length; i++) {
			String currentFlow = flows.get(i);
			expected[i] = currentFlow.substring(0, currentFlow.indexOf(' '));
			/*
			//Need to remove any non-alpha or non-numeric characters
			for(int j = 0; j < expected[i].length(); j++) {
				char c = expected[i].charAt(j);
				if(!((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122))) {
					expected[i] = expected[i].substring(0,expected[i].indexOf(c)) +
							expected[i].substring(expected[i].indexOf(c+1));
				}
			}*/
		}
		String[] actual = testingWorkLoad.getFlowNames();
		System.out.println(Arrays.toString(expected));
		System.out.println(Arrays.toString(actual));
		assertEquals(Arrays.toString(expected),Arrays.toString(actual));
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
		WorkLoadDescription getFlowName = new WorkLoadDescription(testingFile);
		String flow = getFlowName.visualization().get(4);
		String flowName = flow.substring(0, flow.indexOf(' '));
		//System.out.println(flow);
		
		//Expected:
		/*
		 * Need to find how total transmission attempts are calculated. Is not linear in
		 * default WorkLoad.
		 */
		
		//Actual:
		Integer actual = testingWorkLoad.getTotalTxAttemptsInFlow(flowName);
		//System.out.println(actual);
		fail("Not yet implemented");
	}

	@Test
	void testGetNumTxAttemptsPerLink() {
		fail("Not yet implemented");
	}

	@Test
	void testMaxFlowLength() {

		var maxFL = 4;
		assertEquals(maxFL, testingWorkLoad.maxFlowLength());
		
//		String testingFile2 = "Example.txt";
//		WorkLoad tester2 = new WorkLoad(0.9, 0.99, testingFile2);
//		var maxFL2 = 3;
//		assertEquals(maxFL2, tester2.maxFlowLength());
				
	}

}
