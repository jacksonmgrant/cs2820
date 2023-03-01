# CS2820 Spring 2023 WARP Project Code
This code base will be used for the University of Iowa CS 2820 Introduction to Software
Development course. The code was developed by Steve Goddard for the WARP sensor network 
research project. It was first written in Swift and rewritten in Java. It was then 
rewritten again in an object-oriented programming style. It was a quick
hack, and it needs a lot of cleanup and refactoring. A perfect code base to teach
the value of software developement fundamentals!

<br>

## HW 0
This marks the initial commit for the class. No code changes were made. See the 
assignment here for basic git and eclipse instructions: 
https://uiowa.instructure.com/courses/203756/files/21924301?module_item_id=6270600

<br>

## HW 1
Refactored WorkloadDescription for standalone testing. It can now pull information from
Warp test txt files, sort them, and print them to the console with clear formatting.
Learned to analyze the code base for existing resources when solving a problem.

<br>

## HW2
Worked with Nalini Singh to add JavaDoc comments to Flow, Program, ProgramVisualiztion
VisualizationImplementation, Warp, and WorkLoad. Generated JavaDocs are included in
project.

Jackson: Added Javadoc comments to WorkLoad.java (Class comment, attributes, constructors, 22 methods), 
Program.java (3 methods), and ProgramVisualization (Class comment, attributes, all methods). 

Nalini: Added Javadoc comments to Warp (Class comment, attributes, all methods),
Flow (Class comment, attributes, all methods), VisualizationImplementation (Class comment, attributes, all methods).

<br>

## HW3
Nalini: Developed test cases for addNodeToFlow, getFlowPriority, getFlowTxAttemptsPerLink, getNodeNamesOrderedAlphabetically, getNodeIndex, getHyperPeriod,
maxFlowLength, setFlowDeadline

Jackson: Developed test cases for addFlow, getTotalTxAttemptsInFlow, getFlowPriority, setFlowsInRMorder, getFlowNames,
getNodesInFlow, getNumTxAttemptsInLink, and getFlowDeadline.

<br>

## HW4
Learned how to generate and edit UML diagrams for existing code. Generated diagrams for: SchedulableObject, WorkLoad (+parents and associated classes),
and Reliability (+associated classes).
