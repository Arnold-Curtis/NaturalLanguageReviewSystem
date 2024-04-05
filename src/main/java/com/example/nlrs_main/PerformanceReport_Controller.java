package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PerformanceReport_Controller {
    @FXML
    private AnchorPane chartPane;

    private String lecturerID;
    private String unitName;
    private PerformanceReport performanceReport;

    @FXML
    private PieChart pieChart;

    @FXML
    private ListView<String> strengthsListView;

    @FXML
    private ListView<String> weaknessesListView;

    @FXML
    private ListView<String> nuetralListView;

    @FXML
    private Label performanceCommentLabel;

    @FXML
    private Label performanceCommentLabel1;

    @FXML
    private Label performanceCommentLabel2;
    @FXML
    private Label message;

    @FXML
    private TextFlow reportAnalysisDescription;

    @FXML
    private TextArea commentTxtID;

    @FXML
    private Button submitBT1;

    public void initialize(String lecturerID, String unitName) {
        this.lecturerID = lecturerID;
        this.unitName = unitName;
        initializeUI();
    }
    @FXML
    public void submitClickEvent(Event event){
        storeLecturerComment(lecturerID, unitName);
    }
    private void initializeUI() {
        performanceReport = new PerformanceReport(lecturerID, unitName);
        pieChart.setData(performanceReport.getPieChartData());
        strengthsListView.setItems(performanceReport.getStrengths());
        weaknessesListView.setItems(performanceReport.getWeaknesses());
        nuetralListView.setItems(performanceReport.getNeutrals());

        if (performanceReport.getPerformanceComment().equals("Amazing")){
            performanceCommentLabel.setText(performanceReport.getPerformanceComment());
        } else if (performanceReport.getPerformanceComment().equals("Good")) {
            performanceCommentLabel1.setText(performanceReport.getPerformanceComment());
        } else {
            performanceCommentLabel2.setText(performanceReport.getPerformanceComment());
        }
        printDescriptionOnTextFlow();
    }
    public void printDescriptionOnTextFlow(){
        StringBuilder description = new StringBuilder();

        int weaknessesCount = performanceReport.getWeaknesses().size();
        int strengthsCount = performanceReport.getStrengths().size();
        int neutralsCount = performanceReport.getNeutrals().size();

        if (weaknessesCount > strengthsCount && weaknessesCount > neutralsCount) {
            description.append("According to the performance report chart, you need to work extremely hard because you " +
                    "have more weaknesses. \n\n" +
                    "");
            description.append("You have a negative performance report review. ");
            description.append("This means students do not agree with your methods of lecturing them, hence you have ")
                    .append(weaknessesCount).append(" weaknesses\n");

        } else if (strengthsCount > weaknessesCount && strengthsCount > neutralsCount) {
            description.append("Congratulations! According to the performance report chart, you are doing great. ");
            description.append("You have more strengths, and your overall performance is positive. ");
            description.append("Keep up the good work!\n");
        } else {
            description.append("Your performance is average. ");
            description.append("You have equal or nearly equal strengths and weaknesses. ");
            description.append("You need to work extra hard to improve.\n");
        }

        if (strengthsCount > 0) {
            description.append("\nDespite all the stated reviews, a small number of students did find your lectures " +
                    "good. ");
            description.append("You excelled in the following categories:\n\n");

            // Append neutrals
            ObservableList<String> strengthsList = performanceReport.getStrengths();
            for (String strengths : strengthsList) {
                description.append("- ").append(strengths).append("\n");
            }
        }

        description.append("\nTake a look at well summarized lecturing Skills in the sections below:");

        Text descriptionText = new Text(description.toString());
        reportAnalysisDescription.getChildren().add(descriptionText);
    }
    public void storeLecturerComment(String lecturerID, String unitName){
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();

            if (dbConnect != null) {
                String comment = commentTxtID.getText();

                String query = "INSERT INTO lecfeedback (userID, comment, unit) VALUES (?, ?, ?)";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, lecturerID);
                statement.setString(2, comment);
                statement.setString(3, unitName);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Feedback stored successfully");
                    message.setText("Feedback Submitted successfully");
                } else {
                    System.out.println("Failed to store feedback");
                    message.setText("Failed to store feedback");
                }
                statement.close();
            } else {
                System.out.println("Database Connection Failed");
                message.setText("Database Connection Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
            message.setText("Error occurred: " + e.getMessage());

        }

    }
}