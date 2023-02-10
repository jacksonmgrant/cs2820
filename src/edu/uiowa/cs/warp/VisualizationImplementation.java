/**
 * 
 */
package edu.uiowa.cs.warp;

import java.io.File;

/**
 * VisualizationImplementation class implements the Visualization file,
 * helping create visualizations for the WARP project. 
 * 
 * @author sgoddard
 * @version 1.5
 */
public class VisualizationImplementation implements Visualization {

  /**
 * Used to visualize the function description.
 */
  private Description visualization;
  /**
 * Used as a description for file content.
 */
  private Description fileContent;
  /**
 * Used as the window specifically for Gui visualization.
 */
  private GuiVisualization window;
  /**
 * The default set file name.
 */
  private String fileName;
  /**
 * The file used for input, comes from work load.
 */
  private String inputFileName;
  /**
 * The input file name that will be created.
 */
  private String fileNameTemplate;
  /**
 * Initialize the file manager to null.
 * This will be the input parameter. 
 */
  private FileManager fm = null;
  /**
 * Initialize the warp interface to null.
 * This will be the input parameter. 
 */
  private WarpInterface warp = null;
  /**
 * Initialize the work load to null.
 * This will be the input parameter. 
 */
  private WorkLoad workLoad = null;
  /**
 * The object used for visualization.
 */
  private VisualizationObject visualizationObject;


  /**
   * Constructor that initializes objects, and makes a call to 
   * VisualizationObject. This creates a visualization for warp.
 * @param warp
 * @param outputDirectory
 * @param choice
 */
  public VisualizationImplementation(WarpInterface warp, String outputDirectory,
      SystemChoices choice) {
    this.fm = new FileManager();
    this.warp = warp;
    inputFileName = warp.toWorkload().getInputFileName();
    this.fileNameTemplate = createFileNameTemplate(outputDirectory);
    visualizationObject = null;
    createVisualization(choice);
  }

  /**
   * Constructor that initializes objects, and makes a call to 
   * VisualizationObject. This creates a visualization for work load.
 * @param workLoad
 * @param outputDirectory
 * @param choice
 */
  public VisualizationImplementation(WorkLoad workLoad, String outputDirectory,
      WorkLoadChoices choice) {
    this.fm = new FileManager();
    this.workLoad = workLoad;
    inputFileName = workLoad.getInputFileName();
    this.fileNameTemplate = createFileNameTemplate(outputDirectory);
    visualizationObject = null;
    createVisualization(choice);
  }

  /**
 * Displays the visualization if parameters are not null.
 */
@Override
  public void toDisplay() {
    // System.out.println(displayContent.toString());
    window = visualizationObject.displayVisualization();
    if (window != null) {
      window.setVisible();
    }
  }

  /**
 * A file that is created from the file name and the file content as a string.
 */
@Override
  public void toFile() {
    fm.writeFile(fileName, fileContent.toString());
  }

  /**
 * @return visualization as a string
 */
@Override
  public String toString() {
    return visualization.toString();
  }

  /**
   * Depending on the system choice, implement a visualization accordingly.
 * @param choice
 */
  private void createVisualization(SystemChoices choice) {
    switch (choice) { // select the requested visualization
      case SOURCE:
        createVisualization(new ProgramVisualization(warp));
        break;

      case RELIABILITIES:
        // TODO Implement Reliability Analysis Visualization
        createVisualization(new ReliabilityVisualization(warp));
        break;

      case SIMULATOR_INPUT:
        // TODO Implement Simulator Input Visualization
        createVisualization(new NotImplentedVisualization("SimInputNotImplemented"));
        break;

      case LATENCY:
        // TODO Implement Latency Analysis Visualization
        createVisualization(new LatencyVisualization(warp));
        break;

      case CHANNEL:
        // TODO Implement Channel Analysis Visualization
        createVisualization(new ChannelVisualization(warp));
        break;

      case LATENCY_REPORT:
        createVisualization(new ReportVisualization(fm, warp,
            new LatencyAnalysis(warp).latencyReport(), "Latency"));
        break;

      case DEADLINE_REPORT:
        createVisualization(
            new ReportVisualization(fm, warp, warp.toProgram().deadlineMisses(), "DeadlineMisses"));
        break;

      default:
        createVisualization(new NotImplentedVisualization("UnexpectedChoice"));
        break;
    }
  }

  /**
   * Depending on the workload choice, implement a visualization accordingly.
 * @param choice
 */
  private void createVisualization(WorkLoadChoices choice) {
    switch (choice) { // select the requested visualization
      case COMUNICATION_GRAPH:
        // createWarpVisualization();
        createVisualization(new CommunicationGraph(fm, workLoad));
        break;

      case GRAPHVIZ:
        createVisualization(new GraphViz(fm, workLoad.toString()));
        break;

      case INPUT_GRAPH:
        createVisualization(workLoad);
        break;

      default:
        createVisualization(new NotImplentedVisualization("UnexpectedChoice"));
        break;
    }
  }

  /**
   * Creates a visualization and file visualization for the input object.
   * A file is created in the output directory from the file name template.
   * The created object is displayed. 
 * @param <T> 
 * @param obj
 */
  private <T extends VisualizationObject> void createVisualization(T obj) {
    visualization = obj.visualization();
    fileContent = obj.fileVisualization();
    /* display is file content printed to console */
    fileName = obj.createFile(fileNameTemplate); // in output directory
    visualizationObject = obj;
  }

  /**
   * @return the file name template created from the output path and input string.
 * @param outputDirectory 
 */
  private String createFileNameTemplate(String outputDirectory) {
    String fileNameTemplate;
    var workingDirectory = fm.getBaseDirectory();
    var newDirectory = fm.createDirectory(workingDirectory, outputDirectory);
    // Now create the fileNameTemplate using full output path and input filename
    if (inputFileName.contains("/")) {
      var index = inputFileName.lastIndexOf("/") + 1;
      fileNameTemplate = newDirectory + File.separator + inputFileName.substring(index);
    } else {
      fileNameTemplate = newDirectory + File.separator + inputFileName;
    }
    return fileNameTemplate;
  }

}
