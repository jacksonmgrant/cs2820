package edu.uiowa.cs.warp;

/**
 * Develops a visualization of a Warp graph schedule formatted as a table
 * using arrays. Each column of the table is a time slot, and the rows 
 * contain the schedule's instructions. The visualization can be displayed
 * as a GuiVisualization with the displayVisualization() method. A header
 * and footer can also be added with additional information about the Warp
 * graph.
 * 
 * @author sgoddard
 * @version 1.5
 * 
 */
public class ProgramVisualization extends VisualizationObject {

  /**
   * The file type suffix for output files.
   */
  private static final String SOURCE_SUFFIX = ".dsl";
  
  /**
   * A schedule generated by a Warp graph.
   */
  private ProgramSchedule sourceCode;
  
  /**
   * A Warp graph represented as a Program.
   */
  private Program program;
  
  /**
   * Indicates whether or not the Warp graph meets its deadlines.
   */
  private Boolean deadlinesMet;

  /**
   * Creates and initializes a new visualization.
   * 
   * @param warp An interface to interact with a Warp graph
   */
  ProgramVisualization(WarpInterface warp) {
    super(new FileManager(), warp, SOURCE_SUFFIX);
    this.program = warp.toProgram();
    this.sourceCode = program.getSchedule();
    this.deadlinesMet = warp.deadlinesMet();
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
   * Creates a header for the program visualization. Header includes the Scheduler Name, 
   * number of faults, minimum packet reception rate, end to end reliability, and the 
   * number of channels.
   * 
   * @return a Description containing the header
   */
  @Override
  protected Description createHeader() {
    Description header = new Description();

    header.add(createTitle());
    header.add(String.format("Scheduler Name: %s\n", program.getSchedulerName()));

    /* The following parameters are output based on a special schedule or the fault model */
    if (program.getNumFaults() > 0) { // only specify when deterministic fault model is assumed
      header.add(String.format("numFaults: %d\n", program.getNumFaults()));
    }
    header.add(String.format("M: %s\n", String.valueOf(program.getMinPacketReceptionRate())));
    header.add(String.format("E2E: %s\n", String.valueOf(program.getE2e())));
    header.add(String.format("nChannels: %d\n", program.getNumChannels()));
    return header;
  }

  /**
   * Creates a footer for the program visualization. The footer consists of a message indicating
   * whether or not all flows meet their deadlines.
   * 
   * @return a Description containing the footer
   */
  @Override
  protected Description createFooter() {
    Description footer = new Description();
    String deadlineMsg = null;

    if (deadlinesMet) {
      deadlineMsg = "All flows meet their deadlines\n";
    } else {
      deadlineMsg = "WARNING: NOT all flows meet their deadlines. See deadline analysis report.\n";
    }
    footer.add(String.format("// %s", deadlineMsg));
    return footer;
  }


  /**
   * Creates a list of column names which indicate the time slot each column
   * represents.
   * 
   * @return the String array of names
   */
  @Override
  protected String[] createColumnHeader() {
    var orderedNodes = program.toWorkLoad().getNodeNamesOrderedAlphabetically();
    String[] columnNames = new String[orderedNodes.length + 1];
    columnNames[0] = "Time Slot"; // add the Time Slot column header first
    /* loop through the node names, adding each to the header */
    for (int i = 0; i < orderedNodes.length; i++) {
      columnNames[i + 1] = orderedNodes[i];
    }
    return columnNames;
  }

  /**
   * Creates a 2D array used to visualize the schedule as a table with each
   * column a sequential time slot, and the table values are the schedule
   * instructions.
   * 
   * @return a 2D string array of the schedule
   */
  @Override
  protected String[][] createVisualizationData() {
    if (visualizationData == null) {
      int numRows = sourceCode.getNumRows();
      int numColumns = sourceCode.getNumColumns();
      visualizationData = new String[numRows][numColumns + 1];

      for (int row = 0; row < numRows; row++) {
        visualizationData[row][0] = String.format("%s", row);
        for (int column = 0; column < numColumns; column++) {
          visualizationData[row][column + 1] = sourceCode.get(row, column);
        }
      }
    }
    return visualizationData;
  }

  /**
   * @return the title for the visualization
   */
  private String createTitle() {
    return String.format("WARP program for graph %s\n", program.getName());
  }
}
