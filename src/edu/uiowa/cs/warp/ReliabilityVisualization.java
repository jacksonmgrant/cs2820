package edu.uiowa.cs.warp;

import java.util.ArrayList;

/**
 * ReliabilityVisualization creates the visualizations for
 * the reliability analysis of the WARP program. File created
 * by Professor Steve Goddard, createVisualizationData() was
 * written by Andy Luo, and the constructors and other methods
 * were written by Jackson Grant.<p>
 * 
 * CS2820 Spring 2023 Project: Implement this class to create
 * the file visualization that is requested in Warp.
 * 
 * 
 * @author sgoddard
 * @author Jackson Grant
 * @author Andy Luo
 *
 */
public class ReliabilityVisualization  extends VisualizationObject {

	private static final String SOURCE_SUFFIX = ".ra";
	private static final String OBJECT_NAME = "Reliability Analysis";
	
	/**
	 * An interface for interacting with a warp system.
	 */
	private WarpInterface warp;
	
	/**
	 * A reliability analysis of the flows in the given warp system.
	 */
	private ReliabilityAnalysis ra;
	
	
	/**
	 * Creates and initializes a new visualization.
	 * 
	 * @param warp a warp system
	 */
	ReliabilityVisualization(WarpInterface warp) {
		super(new FileManager(), warp, SOURCE_SUFFIX);
		this.warp = warp;
		this.ra = warp.toReliabilityAnalysis();
	}
	
	/**
	   * Combines the title, column headers, and schedule visualization to create a new 
	   * GuiVisualization object.
	   * 
	   * @return the GuiVisualization object
	   */
	  @Override
	  public GuiVisualization displayVisualization() {
	    return new GuiVisualization(createTitle(), createColumnHeader(), createVisualizationData());
	  }
	
	/**
	 * Creates a header for the reliability visualization. Header includes
	 * the title, scheduler name, minimum packet reception rate (M), the
	 * required end-to-end reliability for each flow (E2E), and the number
	 * of channels (nChannels).
	 * 
	 * @return a Description containing the header
	 */
	@Override
	public Description createHeader() {
		Description header = new Description();

	    header.add(createTitle());
	    
	    Program program = warp.toProgram();
	    header.add(String.format("Scheduler Name: %s\n", program.getSchedulerName()));
	    header.add(String.format("M: %s\n", String.valueOf(program.getMinPacketReceptionRate())));
	    header.add(String.format("E2E: %s\n", String.valueOf(program.getE2e())));
	    header.add(String.format("nChannels: %d\n", program.getNumChannels()));
	    return header;
	}
	
	/**
	 * Helper method for createHeader used to generate the title of the 
	 * visualization.
	 * 
	 * @return the title of the visualization
	 */
	public String createTitle() {
		return String.format("Reliability Analysis for graph %s\n", warp.toProgram().getName());
	}
	
	/**
	 * Creates a list of column names formatted as Flowname:Nodename consisting of
	 * every flow in the input file, sorted by priority order of flows then order
	 * of nodes in the flow.
	 * 
	 * @return the String array of column names
	 */
	@Override
	public String[] createColumnHeader() {
		ArrayList<String> columnHeaderList = new ArrayList<String>(0);
		ArrayList<String> flowNames = this.warp.toWorkload().getFlowNamesInPriorityOrder();
		for(String flow: flowNames) {
			String[] nodes = this.warp.toWorkload().getNodesInFlow(flow);
			for(String node: nodes) {
				columnHeaderList.add(flow + ":" + node);
			}
		}
		int numCols = columnHeaderList.size();
		String[] columnHeader = new String[numCols];
		columnHeader = columnHeaderList.toArray(columnHeader);
		return columnHeader;
	}
	
	/**
	 * Creates a 2D array to visualize the reliability data with each column
	 * the reliability of sending a message to the given node in the given flow
	 * and each row a separate timeslot. The table values are the reliabilities.
	 * 
	 * @return a 2D String array of the data
	 */
	@Override
	public String[][] createVisualizationData(){
		ReliabilityTable input = ra.getReliabilities();
		String[][] ans = new String[input.getNumRows()][input.getNumColumns()];
		for(int i = 0; i < input.getNumRows(); i++) {
			for(int j = 0; j < input.getNumColumns(); j++) {
				ans[i][j] = "" + input.get(i,j);
			}
		}
		return ans;
	}
	
	
	
/* File Visualization for workload defined in Example.txt follows. Note
 * that your Authentication tag will be different from this example. The
 * rest of your output in the file ExamplePriority-0.9M-0.99E2E.ra
 * should match this output, where \tab characters are used a column
 * delimiters.
// Course CS2820 Authentication Tag: r3XWfL9ywZO36jnWMZcKC2KTB2hUCm3AQCGxREWbZRoSn4/XdrQ/QuNQvtzAxeSSw55bWTXwbI9VI0Om+mEhNd4JC2UzrBBrXnHmsbPxbZ8=
Reliability Analysis for graph Example created with the following parameters:
Scheduler Name:	Priority
M:	0.9
E2E:	0.99
nChannels:	16
F0:A	F0:B	F0:C	F1:C	F1:B	F1:A
1.0	0.9	0.0	1.0	0.0	0.0
1.0	0.99	0.81	1.0	0.0	0.0
1.0	0.999	0.972	1.0	0.0	0.0
1.0	0.999	0.9963	1.0	0.0	0.0
1.0	0.999	0.9963	1.0	0.9	0.0
1.0	0.999	0.9963	1.0	0.99	0.81
1.0	0.999	0.9963	1.0	0.999	0.972
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
1.0	0.999	0.9963	1.0	0.999	0.9963
*/
}
