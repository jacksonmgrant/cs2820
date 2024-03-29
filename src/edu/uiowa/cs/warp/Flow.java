package edu.uiowa.cs.warp;

import java.util.ArrayList;

/**
 * The Flow class extends the SchedulableObject file and implements Comparable.
 * It keeps track of the flows used for the WARP project.
 * @author sgoddard
 *
 */
public class Flow extends SchedulableObject implements Comparable<Flow>{

	/**
	 * The value to return if parameters are undefined.
	 */
	private static final Integer UNDEFINED = -1;
	/**
	 * The default number of faults permitted.
	 */
	private static final Integer DEFAULT_FAULTS_TOLERATED = 0; 
	/**
	 * The default starting point.
	 */
	private static final Integer DEFAULT_INDEX = 0;
	/**
	 * The default set for the period.
	 */
	private static final Integer DEFAULT_PERIOD = 100; 
	/**
	 * The default set for the deadline.
	 */
	private static final Integer DEFAULT_DEADLINE = 100;
	/**
	 * The default set for the phase.
	 */
	private static final Integer DEFAULT_PHASE = 0;
	

    Integer initialPriority = UNDEFINED;
    Integer index;  // order in which the node was read from the Graph file
    Integer numTxPerLink; //  determined by fault model
    ArrayList<Node> nodes; // Flow src is 1st element and flow snk is last element in array
    /*
     *  nTx needed for each link to reach E2E reliability target. Indexed by src node of the link. 
     *  Last entry is total worst-case E2E Tx cost for schedulability analysis
     */
    ArrayList<Integer> linkTxAndTotalCost; 
    ArrayList<Edge> edges; //used in Partition and scheduling
    Node nodePredecessor;
    Edge edgePredecessor;
    
    /**
     * Constructor that sets name, priority, and index.
     * 
     * @param name the name of the flow
     * @param priority the priority of the flow
     * @param index the index of the flow
     */
    Flow (String name, Integer priority, Integer index){
    	super(name, priority, DEFAULT_PERIOD, DEFAULT_DEADLINE, DEFAULT_PHASE);
    	this.index = index;
        /*
         *  Default numTxPerLink is 1 transmission per link. Will be updated based
         *  on flow updated based on flow length and reliability parameters
         */
        this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
        this.nodes = new ArrayList<>();
        this.edges  = new ArrayList<>();
        this.linkTxAndTotalCost = new ArrayList<>();
        this.edges = new ArrayList<>();	
        this.nodePredecessor = null;
        this.edgePredecessor = null;
    }
    
    /**
     * Constructor that sets the index, numTxPerLink, nodePredecessor, and edgePredecessor.
     * It also initializes an ArrayList each for nodes, linkTxAndTotalCost, and edges.
     */
    Flow () {
    	super();
    	this.index = DEFAULT_INDEX;
    	/*
    	 *  Default numTxPerLink is 1 transmission per link. Will be updated based
    	 *  on flow updated based on flow length and reliability parameters
    	 */
    	this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
    	this.nodes = new ArrayList<>();
    	this.linkTxAndTotalCost = new ArrayList<>();
    	this.edges = new ArrayList<>();
    	this.nodePredecessor = null;
        this.edgePredecessor = null;
    }

	/**
	 * @return the initialPriority
	 */
	public Integer getInitialPriority() {
		return initialPriority;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @return the numTxPerLink
	 */
	public Integer getNumTxPerLink() {
		return numTxPerLink;
	}

	/**
	 * @return the nodes
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	/**
	 * @return the edges
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	/**
	 * Add an edge to the flow.
	 */
	public void addEdge(Edge edge) {
		/* set predecessor and add edge to flow */
		edge.setPredecessor(edgePredecessor);
		edges.add(edge);
		/* update predecessor for next edge added */
		edgePredecessor = edge;
	}
	
	/**
	 * Add a node to the flow.
	 */
	public void addNode(Node node) {
		/* set predecessor and add edge to flow */
		node.setPredecessor(nodePredecessor);
		nodes.add(node);
		/* update predecessor for next edge added */
		nodePredecessor = node;
	}
	/**
	 * @return the linkTxAndTotalCost
	 */
	public ArrayList<Integer> getLinkTxAndTotalCost() {
		return linkTxAndTotalCost;
	}

	/**
	 * @param initialPriority the initialPriority to set
	 */
	public void setInitialPriority(Integer initialPriority) {
		this.initialPriority = initialPriority;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @param numTxPerLink the numTxPerLink to set
	 */
	public void setNumTxPerLink(Integer numTxPerLink) {
		this.numTxPerLink = numTxPerLink;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @param linkTxAndTotalCost the linkTxAndTotalCost to set
	 */
	public void setLinkTxAndTotalCost(ArrayList<Integer> linkTxAndTotalCost) {
		this.linkTxAndTotalCost = linkTxAndTotalCost;
	}

	/**
	 * @return an int from a comparison, returning either -1 or 1
	 * If the priority of the input parameter flow is greater than the priority of this object, 
	 * then return -1, else return 1.
	 */
	@Override
    public int compareTo(Flow flow) {
    	// ascending order (0 is highest priority)
        return flow.getPriority() > this.getPriority() ? -1 : 1;
    }
    
    /**
     * @return a string from method getName()
     */
    @Override
    public String toString() {
        return getName();
    }
    
}
