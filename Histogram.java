// Written by: Kumar634

package com.example.hw5_2081;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math;

public class Histogram {
    
    
    Bin[] histogram = new Bin[200];
    
    public int userDefinedInterval = 0;
    public float[] windValues = new float[10000];
    public float numWindValues;
    public float K;
    
    public Bin[] getHistogram() {
        for(int i = 0; i < 200; i++) {
            histogram[i] = new Bin(i * userDefinedInterval, 0, 0);
        }
        return histogram;
    }
    
    //Getting the data from the file
    public void getData(String filePath) throws FileNotFoundException {
        String input = " ";
        Scanner sysInput = new Scanner(System.in);  
        Scanner fileInput;
        
        while(true) { // while this is true the loop will read through the file that is intiated below
            System.out.println("Enter a file to read- (You can type 'Main' to not have to type Augspurger_2017_08.csv");
            input = sysInput.nextLine();        
            
            if(input.equals("Main")) { //Suggested by a TA to make it easier instead of having to type the filename each time
                filePath = "/Users/rohankumar/Desktop/HW5_2081/src/main/java/com/example/hw5_2081/Augspurger_2017_08.csv";
            }
            else {filePath = input;}
            
            File f = new File(filePath);
            
            try {
                fileInput = new Scanner(f);
                break;
            }
            catch(Exception e) { // General exceptions caught
                System.out.println(e);
            }
        }
        
        if(userDefinedInterval == 0) {     
            System.out.println("Enter an Interval between 50 and 200"); // This is print asking for an input between the stated intervals
            
            
            input = sysInput.nextLine();
            userDefinedInterval = Integer.parseInt(input);
        }
        for(int i = 0; i < 200; i++) {   // THis is based on the user input intervals
            if(i == 0) {
                histogram[i].interval = (float) 0.0;
            }
            histogram[i].interval = Float.parseFloat(String.valueOf(userDefinedInterval * i));
        }
    
        String[] splitLine;
        String line;
    
        // Skip the first 7 lines in Ausgpurger_2017_08.csv file as they are redundant
        for (int i = 0; i < 7; i++) {
            if (fileInput.hasNextLine()) {
                line = fileInput.nextLine();
            } else {
                System.out.println("Error: not enough lines in the input file");
                return;
            }
        }
    
        // Process the remaining lines in the file
        int counter = 0;
        while (fileInput.hasNextLine()) {
            line = fileInput.nextLine();
            splitLine = line.split(",");
        
            if (splitLine.length < 7) {
                System.out.println("Error: not enough elements in the input line");
                return;
            }
        
            try {
                float value = Float.parseFloat(splitLine[5]);
                windValues[counter] = value;
                counter++;
                numWindValues = counter;
            } catch (NumberFormatException e) {
                System.out.println("Error: unable to parse value from the input line");
            }
        
            for (String element : splitLine) {
                System.out.print(" " + element + " ");
            }
            System.out.println();
        }
        
    }
    
  // Making the Histogram and going through the elements from the data
    public void createHistogram() { // Following from the write up
        for(int i = 0; i < numWindValues; i++) {
            windValues[i] = windValues[i] * windValues[i];
        }
        for(int i = 0; i < numWindValues; i++) {
            for(int j = 0; j < 200; j++) {
                if(windValues[i] >= histogram[j].interval && windValues[i] < histogram[j + 1].interval) { //checks if value is out of the interval
                    histogram[j].count++;
                    break;
                }
            }
        }
    }
    
  // NOrmalizing the data
    public void normalize() throws IOException { // Following from the write up
        float cumulativeP = 1;
        for(int j = 0; j < 200; j++) {   //calculate cumulative probability
            cumulativeP = cumulativeP - histogram[j].count / numWindValues;
            if(cumulativeP < 0.0) {
                break;
            }
            histogram[j].cumProbability = cumulativeP;  //store cumulative probability
        }
        
    }
    
   // Regression Analyis- excluded at 1% level 
    public void analysis() {
        Float Num = (float) 0.0;
        Float Den = (float) 0.0;
        for(int j = 0; j < 200; j++) {
            if(histogram[j].cumProbability <= 0.01) {
                break;
            }
            Num = (float) (Num - Math.log(histogram[j].cumProbability));
            Den = Den + histogram[j + 1].interval;
        }
        K = Num / Den;
    }

    
    // The method below help with calculating probability based on Less than, Greater/Equal To, and Quit
    // I have shortened the form so that L is for Less than, G is for Greater/Equal To, and Q is for quiting the program
    public void probCalculations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 'L' (Less), 'G' (Greater/Equal To), 'Q' to quit: ");
        boolean isQuit = false;
        while(!isQuit) {
            String userInput = scanner.nextLine();
            System.out.println();
            try {
                if(userInput.equalsIgnoreCase("L")) { //Handeling each case
                    System.out.println("Enter wind speed: ");
                    try {
                        float windSpeed = Float.parseFloat(scanner.nextLine());
                        double probability = 1.0 - Math.exp(-K * windSpeed * windSpeed);
                        System.out.println("Probability of wind speed being less than " + windSpeed + " is " + probability);
                        System.out.println("Enter 'L' (Less), 'G' (Greater/Equal To), 'Q' to quit: "); // User is reprompted if they choose to continue
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
    
                }
                else if(userInput.equalsIgnoreCase("G")) { //Handeling each case
                    System.out.println("Enter wind speed: ");
                    try {
                        float windSpeed = Float.parseFloat(scanner.nextLine());
                        double probability = Math.exp(-K * windSpeed * windSpeed);
                        System.out.println("Probability of wind speed being greater than or equal to " + windSpeed + " is " + probability);
                        System.out.println("Enter 'L' (Less), 'G' (Greater/Equal To), 'Q' to quit: ");
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
                }
                else if(userInput.equalsIgnoreCase("Q")) { //Handeling each case
                    File outputFile = new File("cumulativeProbability.txt");
                    FileWriter fileWriter = new FileWriter(outputFile);
    
                    fileWriter.write(String.valueOf(K));
                    fileWriter.write("\n");
                    fileWriter.write(String.valueOf(userDefinedInterval));
                    fileWriter.write("\n");
    
                    for(int i = 0; i < 200; i++) {
                        if(histogram[i].cumProbability == 0.0) {
                            break;
                        }
                        fileWriter.write(String.valueOf(i));
                        fileWriter.write(',');
                        fileWriter.write(String.valueOf(histogram[i].cumProbability));
                        fileWriter.write('\n');
                    }
                    fileWriter.close();
                    isQuit = true;
                } else {
                    System.out.println("Invalid input- Try again.");
                }
            }
            catch(Exception e) {
                System.out.println("Error occurred- Try again.");
            }
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        // Calling all the step above in Main method to compute for the functions
        Histogram histogramMade = new Histogram();
        histogramMade.getHistogram();
        histogramMade.getData("/Users/rohankumar/Desktop/HW5_2081/src/main/java/com/example/hw5_2081/Augspurger_2017_08.csv");
        histogramMade.createHistogram();
        histogramMade.normalize();
        histogramMade.analysis();
        histogramMade.probCalculations();
    }
}
