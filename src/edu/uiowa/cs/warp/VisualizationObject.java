/**
 * 
 */
package edu.uiowa.cs.warp;

/**
 * @author sgoddard
 * @version 1.5
 *
 */
abstract class VisualizationObject {

  private FileManager fm;
  private String suffix;
  private String nameExtension;
  private static final String NOT_IMPLEMENTED = "This visualization has not been implemented.";
  protected String[][] visualizationData;

  VisualizationObject(FileManager fm, WorkLoad workLoad, String suffix) {
    this.fm = fm;
    this.nameExtension = String.format("-%sM-%sE2E",
        String.valueOf(workLoad.getMinPacketReceptionRate()), String.valueOf(workLoad.getE2e()));
    this.suffix = suffix;
    visualizationData = null;
  }

  VisualizationObject(FileManager fm, SystemAttributes warp, String suffix) {
    this.fm = fm;
    if (warp.getNumFaults() > 0) {
      this.nameExtension = nameExtension(warp.getSchedulerName(), warp.getNumFaults());
    } else {
      this.nameExtension =
          nameExtension(warp.getSchedulerName(), warp.getMinPacketReceptionRate(), warp.getE2e());
    }
    this.suffix = suffix;
    visualizationData = null;
  }

  VisualizationObject(FileManager fm, SystemAttributes warp, String nameExtension, String suffix) {
    this.fm = fm;
    if (warp.getNumFaults() > 0) {
      this.nameExtension =
          nameExtension(warp.getSchedulerName(), warp.getNumFaults()) + nameExtension;
    } else {
      this.nameExtension =
          nameExtension(warp.getSchedulerName(), warp.getMinPacketReceptionRate(), warp.getE2e())
              + nameExtension;
    }

    this.suffix = suffix;
    visualizationData = null;
  }

  VisualizationObject(FileManager fm, String nameExtension, String suffix) {
    this.fm = fm;
    this.nameExtension = nameExtension;
    this.suffix = suffix;
    visualizationData = null;
  }

  private String nameExtension(String schName, Double m, double e2e) {
    String extension =
        String.format("%s-%sM-%sE2E", schName, String.valueOf(m), String.valueOf(e2e));
    return extension;
  }

  private String nameExtension(String schName, Integer numFaults) {
    String extension = String.format("%s-%sFaults", schName, String.valueOf(numFaults));
    return extension;
  }

  /**
   * @return the fm
   */
  public FileManager getFileManager() {
    return fm;
  }

  /**
   * Builds the visualization of the data with column headers, formatted
   * into strings.
   * 
   * @return a Description containing the visualization line by line
   */
  public Description visualization() {
    Description content = new Description();
    var data = createVisualizationData();

    if (data != null) {
      String nodeString = String.join("\t", createColumnHeader()) + "\n";
      content.add(nodeString);

      for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
        var row = data[rowIndex];
        String rowString = String.join("\t", row) + "\n";
        content.add(rowString);
      }
    } else {
      content.add(NOT_IMPLEMENTED);
    }
    return content;
  }

  /**
   * Builds a file for the visualization with the proper extension and suffix
   * attached to the given name.
   * 
   * @param fileNameTemplate the name to build a file name from
   * @return the name of the created file
   */
  public String createFile(String fileNameTemplate) {
    return fm.createFile(fileNameTemplate, nameExtension, suffix);
  }

  /**
   * Combines the header, data visualization, and footer into one Description
   * containing the file contents line by line.
   * 
   * @return a Description containing the file content
   */
  public Description fileVisualization() {
    Description fileContent = createHeader();
    fileContent.addAll(visualization());
    fileContent.addAll(createFooter());
    return fileContent;
  }

  public GuiVisualization displayVisualization() {
    return null; // not implemented
  }

  protected Description createHeader() {
    Description header = new Description();
    return header;
  }

  protected Description createFooter() {
    Description footer = new Description();
    return footer;
  }

  protected String[] createColumnHeader() {
    return new String[] {NOT_IMPLEMENTED};
  }

  protected String[][] createVisualizationData() {
    return visualizationData; // not implemented--returns null
  }
}
