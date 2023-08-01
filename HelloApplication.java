package com.example.hw5_2081;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;


public class HelloApplication extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        
        // Create the chart
        LineChart<String, Number> chart = createLineChart();
        
        // Add the data series to the chart
        addDataSeries(chart);
        
        // Create the scene with the chart
        Scene scene = new Scene(new StackPane(chart), 700, 600);
        
        // Set the title and scene of the stage
        stage.setTitle("Histogram Analysis of OLS and Discrete Values");
        stage.setScene(scene);
        stage.show();
    }
    
    // Helper method to create the line chart
    private LineChart<String, Number> createLineChart() {
        
        // Define the x and y axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        // Label the x and y axes
        xAxis.setLabel("Wind Speed Squared");
        yAxis.setLabel("Cumulative Probability");
    
        // Set the range of the x-axis
       
        
        // Create the line chart
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        
        // Set the chart style
        chart.setStyle("-fx-background-color: #ffc500");
        chart.setPadding(new Insets(10));
        
        // Return the chart
        return chart;
    }
    
    // Helper method to add the data series to the chart
    private void addDataSeries(LineChart<String, Number> chart) throws IOException {
        
        // Load the data from the file
        Double k = null;
        Double userDefinedInterval = null;
        XYChart.Series<String, Number> cumProbSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> olsSeries = new XYChart.Series<>();
        try (Scanner scanner = new Scanner(new FileInputStream("cumProbability.txt"))) {
            k = Double.parseDouble(scanner.nextLine());
            userDefinedInterval = Double.parseDouble(scanner.nextLine());
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                String xValue = String.valueOf(Double.parseDouble(values[0]) + userDefinedInterval);
                Double yValue = Double.parseDouble(values[1]);
                cumProbSeries.getData().add(new XYChart.Data<>(xValue, yValue));
                Double olsYValue = Math.exp(-k * Double.parseDouble(xValue));
                if (olsYValue > 0.0001) {
                    olsSeries.getData().add(new XYChart.Data<>(xValue, olsYValue));
                }
            }
        }
        
        // Set the names of the series
        cumProbSeries.setName("Cumulative Probabilities per Interval");
        olsSeries.setName("OLS");
        
        // Add the series to the chart
        chart.getData().addAll(cumProbSeries, olsSeries);
    }
    
    // Define the main method
    public static void main(String[] args) {
        launch(args);
    }
    
}
