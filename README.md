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

<br>

## HW5
Worked with Jackie Mills to transfer numTxPerLinkAndTotalTxCost to the ReliabilityAnalysis class and refactor the method.

<br>

# Final Project

[Technical Specification Google Doc](https://docs.google.com/document/d/1YtgiXZ6dUqEybnEn1GiRPgqbS9u9EbHHVdMxRW4qAtI/edit?usp=sharing)

## Sprint 1 - High Level Plans

### Sprint 1

* Fill out this document -Group
* Put high-level plans into ReadMe -Jackie, Andy
* Create a full sequence diagram for when Warp is run with the -ra option -Matt, Jackson
* Create plans and assign work -Group

### Sprint 2

* Update ReadMe to reflect updated plans and document who did what -Jackie
* Fully code ReliabilityVisualization
    * Implement createHeader() -Jackson
    * Implement createVisualizationData() -Andy
    * Implement visualization() -Andy
* JavaDoc ReliabilityVisualization -Group, Jackson Review
* Update UML diagrams -Matt
* Make tests for ReliabilityVisualization - Jackie, Matt
    * Unit tests for each method -Matt
    * Test to verify output formatting -Matt
    * Test to check math -Jackie
* Plan Sprint 3 -Group

### Sprint 3

* Update ReadMe - Jackie
* Fully code ReliabilityAnalysis
    * Bug fix (2.a.iii.1) -Jackson
    * Run reliability analysis from constructor -Jackson, Andy
    * Implement getReliabilities() -Andy
    * Implement verifyReliabilities() -Andy
* JavaDoc ReliabilityAnalysis -Group, Jackson Review
* Update UML diagrams -Matt
* Make tests for ReliabilityAnalysis -Jackie, Matt
    * Unit tests for each method -Jackie, Matt
    * Test to verify output data -Jackie, Matt

## Sprint 2 - Coding ReliabilityVisualization and it's tests

* Update ReadMe to reflect updated plans and document who did what -Jackie
* Fully code ReliabilityVisualization
    * Implement createHeader() -Jackson
    * Implement createHeader() -Jackson
    * Implement createColumnHeader() -Jackson
    * Implement createVisualizationData() -Andy
* JavaDoc all things worked on -Group with review by Jackson
* UML Diagram update -Matt
* Tests for ReliabilityVisualization
    * Create testCreateColumnHeader() -Jackie
    * Create testCreateHeader() -Matt
    * Create testCreateVisualizationData() -Matt
    * Create testCreateTitle() -Jackson
* Continued planning Sprint 3 and stub out methods & tests -Group

## Sprint 2 - Coding ReliabilityAnalysis and it's tests
* Update ReadMe to document who finished each task -Jackie
* Fully code ReliabilityAnalysis
    * Bug fix from HW5 -Jackson
    * Run reliability analysis from constructor with the following methods:
        * ReliabilityAnalysis(program) -Jackson
        * buildNodeMap() -Jackson
        * buildReliabilityTable() -Jackson
        * setInitialStateForReleasedFlows() -Jackson
        * carryForwardReliabilities() -Jackson
        * updateTable() -Jackson
        * setReliabilities() -Jackson
        * setReliabilityHeaderRow() -Jackson
        * getReliabilityHeaderRow() -Jackson
        * printRATable() -Jackson
    * Implement getReliabilities() -Andy
    * Implement verifyReliabilities() -Andy
    * Implement getFinalReliabilityRow() -Andy
* JavaDoc ReliabilityAnalysis and ReliabilityAnalysisTest -Group with review by Jackson
* Update UML diagrams -Matt
* Make tests for ReliabilityAnalysis
    * testVerifyReliabilities() -Jackie
    * testVerifyReliabilitiesStress() -Jackie , Andy
    * testCarryForwardReliabilities() -Jackie
    * testSetReliabilities() -Jackie
    * testSetReliabilityHeaderRow() -Jackie
    * testSetReliabilityHeaderRowStress() -Jackie
    * testGetReliabilities() -Matt
    * testBuildReliabilityTable() -Matt
    * testPrintRaTable() -Matt
    * testSetInitialStateForReleasedFlows() -Matt
        
