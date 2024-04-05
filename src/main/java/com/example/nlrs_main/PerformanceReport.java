package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PerformanceReport {
    private String lecturerID;
    private String unitName;
    private Map<Integer, Double> categoryScores;
    private double totalScore;

    public PerformanceReport(String lecturerID, String unitName) {
        this.lecturerID = lecturerID;
        this.unitName = unitName;
        this.categoryScores = new HashMap<>();
        fetchData();
    }

    private void fetchData() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT CaID, AVG(CoScore) AS avg_score FROM feedback WHERE userID = ? AND unit = ? GROUP BY CaID";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, lecturerID);
                statement.setString(2, unitName);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int categoryID = resultSet.getInt("CaID");
                    double avgScore = resultSet.getDouble("avg_score");
                    categoryScores.put(categoryID, avgScore);
                }

                // Calculate total score
                totalScore = categoryScores.values().stream().mapToDouble(Double::doubleValue).sum() / categoryScores.size();

                // Store category scores in categoriesscores table
                storeCategoryScores();

                // Store performance report in performancereports table
                storePerformanceReport();

                resultSet.close();
                statement.close();
                dbConnect.close();
            } else {
                System.out.println("Database Connection Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    private void storeCategoryScores() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "INSERT INTO categoryscores (avgCatScore, catID, unitName, lec) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = dbConnect.prepareStatement(query);

                for (Map.Entry<Integer, Double> entry : categoryScores.entrySet()) {
                    int categoryID = entry.getKey();
                    double avgScore = entry.getValue();

                    statement.setDouble(1, avgScore);
                    statement.setInt(2, categoryID);
                    statement.setString(3, unitName);
                    statement.setString(4, lecturerID);
                    statement.executeUpdate();
                }

                statement.close();
                dbConnect.close();
            } else {
                System.out.println("Database Connection Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    private void storePerformanceReport() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "INSERT INTO performancereports (unitsName, userID, score) VALUES (?, ?, ?)";
                PreparedStatement statement = dbConnect.prepareStatement(query);

                statement.setString(1, unitName);
                statement.setString(2, lecturerID);
                statement.setDouble(3, totalScore);

                statement.executeUpdate();
                statement.close();
                dbConnect.close();
            } else {
                System.out.println("Database Connection Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public ObservableList<PieChart.Data> getPieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Double> entry : categoryScores.entrySet()) {
            int categoryID = entry.getKey();
            double avgScore = entry.getValue();
            String categoryName = getCategoryName(categoryID);
            PieChart.Data data = new PieChart.Data(categoryName, avgScore);
            pieChartData.add(data);
        }
        return pieChartData;
    }
    public ObservableList<String> getStrengths() {
        ObservableList<String> strengths = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Double> entry : categoryScores.entrySet()) {
            int categoryID = entry.getKey();
            double avgScore = entry.getValue();
            String categoryName = getCategoryName(categoryID);
            if (avgScore > 5.0) {
                strengths.add(categoryName + ": " + avgScore);
            }
        }
        return strengths;
    }
    public ObservableList<String> getNeutrals() {
        ObservableList<String> weaknesses = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Double> entry : categoryScores.entrySet()) {
            int categoryID = entry.getKey();
            double avgScore = entry.getValue();
            String categoryName = getCategoryName(categoryID);
            if (avgScore == 5.0) {
                weaknesses.add(categoryName + ": " + avgScore);
            }
        }
        return weaknesses;
    }
    public ObservableList<String> getWeaknesses() {
        ObservableList<String> weaknesses = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Double> entry : categoryScores.entrySet()) {
            int categoryID = entry.getKey();
            double avgScore = entry.getValue();
            String categoryName = getCategoryName(categoryID);
            if (avgScore < 5.0) {
                weaknesses.add(categoryName + ": " + avgScore);
            }
        }
        return weaknesses;
    }
    private String getCategoryName(int categoryID) {
        switch (categoryID) {
            case 1:
                return "Knowledge and Expertise";
            case 2:
                return "Teaching Style and Delivery";
            case 3:
                return "Clarity of Explanations";
            case 4:
                return "Engagement and Interaction with Students";
            case 5:
                return "Responsiveness and Availability";
            case 6:
                return "Course Organization and Structure";
            case 7:
                return "Fairness and Objectivity in Grading";
            case 8:
                return "Use of Visual Aids and Technology";
            case 9:
                return "Providing Relevant and Up-to-Date Content";
            case 10:
                return "Encouraging Critical Thinking and Discussion";
            case 11:
                return "Providing Constructive Feedback";
            case 12:
                return "Punctuality and Time Management";
            case 13:
                return "Respect for Diverse Perspectives";
            case 14:
                return "Enthusiasm and Passion for Teaching";
            case 15:
                return "Overall Effectiveness in Facilitating Learning";
            default:
                return "Unknown Category";
        }
    }
    public double getTotalScore() {
        return totalScore;
    }

    public String getPerformanceComment() {
        if (totalScore >= 7.0) {
            return "Amazing";
        } else if (totalScore >= 5.0) {
            return "Good";
        } else if (totalScore >= 3.0) {
            return "You need to do better";
        } else {
            return "Below expectations";
        }
    }
}