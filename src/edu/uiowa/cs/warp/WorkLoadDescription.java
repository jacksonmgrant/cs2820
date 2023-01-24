/*
 * Warp
 *
 * Created by Steve Goddard on 9/18/20
 * Modified by nsingh5 on 1/23/23.
 */ 

package edu.uiowa.cs.warp;

import java.util.ArrayList;
import java.util.Collections;




/**
 * Reads the input file, whose name is passed as input parameter to the constructor, and builds a
 * Description object based on the contents. Each line of the file is an entry (string) in the
 * Description object.
 * 
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
  
  /** 
   * Returns Flows alphabetically.
   *
   * @param args instantiates WorkLoadDescription, prints StressTest.txt alphabetically
  */
  public static void main(String[] args) { 

    WorkLoadDescription sts = new WorkLoadDescription("StressTest.txt"); //instantiate with txt file
    var contents = sts.visualization();
   	
    System.out.println(contents.get(0).substring(0,
        contents.get(0).indexOf("{"))); // print Graph Name without the {
    contents.remove(0); //removes the first line "StressTest" so it doesn't print on console as Flow
    contents.remove(contents.size() - 1); // removes }
 
    Collections.sort(contents);
    for (int i = 0; i < contents.size(); i++) { // loop to print the Flows
      int y = i + 1; // increments index so that Flow starts at 1
      System.out.printf("Flow " + y + ": " + contents.get(i));
    }

  }
  


}
  


