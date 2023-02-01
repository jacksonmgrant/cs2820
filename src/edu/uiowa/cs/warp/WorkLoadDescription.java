/**
 * 
 */
package edu.uiowa.cs.warp;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

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
  
  public static void main(String[] args) {
	  //Get file contents
	  String file = "StressTest.txt";
	  WorkLoadDescription stressTest = new WorkLoadDescription(file);
	  Description toBeAlphabetized = stressTest.visualization();
	  
	  //Parse file name
	  String fileName = stressTest.getInputFileName();
	  
	  //Trim and Sort toBeAlphabetized to only contain flows
	  toBeAlphabetized.remove(0);
	  toBeAlphabetized.remove(toBeAlphabetized.size()-1);
	  Collections.sort(toBeAlphabetized);
	  
	  //Print the file in the proper format
	  System.out.println(fileName.substring(0, fileName.indexOf(".")));
	  for(int i = 0; i < toBeAlphabetized.size(); i++) {
		  System.out.print("Flow " + (i+1) + ": " + toBeAlphabetized.get(i));
	  }
  }
}
