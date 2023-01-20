/**
 * 
 */
package edu.uiowa.cs.warp;

/**
 * Reads the input file, whose name is passed as input parameter to the constructor, and builds a
 * Description object based on the contents. Each line of the file is an entry (string) in the
 * Description object.
 * 
 * @author sgoddard
 * @version 1.4 Fall 2022
 */
public class WorkLoadDescription extends VisualizationObject {

  private static final String EMPTY = "";
  private static final String INPUT_FILE_SUFFIX = ".wld";

  private Description description;
  private String inputGraphString;
  private FileManager fm;
  private String inputFileName;

  WorkLoadDescription(String inputFileName) {
    super(new FileManager(), EMPTY, INPUT_FILE_SUFFIX); // VisualizationObject constructor
    this.fm = this.getFileManager();
    initialize(inputFileName);
  }

  @Override
  public Description visualization() {
    return description;
  }

  @Override
  public Description fileVisualization() {
    return description;
  }

  // @Override
  // public Description displayVisualization() {
  // return description;
  // }

  @Override
  public String toString() {
    return inputGraphString;
  }

  public String getInputFileName() {
    return inputFileName;
  }

  private void initialize(String inputFile) {
    // Get the input graph file name and read its contents
    InputGraphFile gf = new InputGraphFile(fm);
    inputGraphString = gf.readGraphFile(inputFile);
    this.inputFileName = gf.getGraphFileName();
    description = new Description(inputGraphString);
  }
  public static void main(String[] args){
   	WorkLoadDescription sts = new WorkLoadDescription("StressTest.txt"); // Instantiate WorkLoadDescription with the parameter StressTest.txt
   	
    /** Print to the console 
   	i.  Graph Name: {name of graph in the file, sans the ‘{‘ 
   	ii.  Each flow, preceded by the string ‘Flow k: ‘, where k is the flow number starting 
   	at 1, with the flows ordered alphabetically by their name. That is, F2 will be 
   	printed before F5. (Note, however, ordering the flows alphabetically is not the 
   	same as ordering them numerically, which is what I have usually done in the 
   	graph .txt files when creating the flows.) 

   	   * 		for (int i = 1; i < whatever.size(); i++){     
   				        System.out.println("Flow " + i + ": " + whatever.get(i)); 		 
   				}

   	   * 
   	   * 
   	   * 
   	   */
  }
  


}
  


