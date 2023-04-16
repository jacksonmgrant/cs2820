package edu.uiowa.cs.warp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.uiowa.cs.warp.SystemAttributes.ScheduleChoices;

/**
 * ReliabilityVisualization creates the visualizations for
 * the reliability analysis of the WARP program. <p>
 * 
 * CS2820 Spring 2023 Project: Implement this class to create
 * the file visualization that is requested in Warp.
 * 
 * @author sgoddard
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
	 * Creates an initializes a new visualization.
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
		return String.format("Reliability Analysis for graph %s\n", warp.toWorkload().getInputFileName());
	}
	
	/**
	 * Creates a list of column names formatted as Flowname:Nodename consisting of
	 * every flow in the input file.
	 * 
	 * @return the String array of column names
	 */
	@Override
	public String[] createColumnHeader() {
		return ReliabilityColumnHeader.getColumnHeader(warp);
		/*
		WorkLoad workload = warp.toWorkload();
		FlowMap flows = workload.getFlows();
		Set<Entry<String,Flow>> allCombos = flows.entrySet();
		String[] columnNames = new String[allCombos.size()];
		
		Iterator nameGrabber = allCombos.iterator();
		int i = 0;
		while(nameGrabber.hasNext()) {
			Map.Entry<String, Flow> pair = (Entry<String, Flow>) nameGrabber.next();
			String flowName = pair.getKey();
			ArrayList<Node> flowContents = pair.getValue().getNodes();
			for(Node n : flowContents) {
				System.out.print(i + ": ");
				columnNames[i] = flowName + ":" + n.getName();
				System.out.println(columnNames[i]);
				i++;
			}
		}
		return columnNames;*/
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
		//TODO implement this operation
		return null;
	}
	
	
	
	/*
	   * Testing main, feel free to rewrite and/or use whenever you need to test something.
	   * If you want to save a test for reuse, just comment it out when it's not in use.
	   * 
	   * TODO delete this
	   */
	public static void main(String[] args) {
		WorkLoad w = new WorkLoad(0, 0.9, 0.9, "Example1a.txt");
		WarpInterface warp = SystemFactory.create(w, 16, ScheduleChoices.PRIORITY);
		ReliabilityVisualization tester = new ReliabilityVisualization(warp);
		
		
		System.out.println(Arrays.toString(tester.createColumnHeader()));
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
