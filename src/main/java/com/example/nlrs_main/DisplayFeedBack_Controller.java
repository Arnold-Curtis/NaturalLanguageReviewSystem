package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DisplayFeedBack_Controller {

    private String adminID;
    private String unitName;
    @FXML
    private Label subTitle;
    @FXML
    private ListView feedBackListView;
    @FXML
    private Label adminNameId;
    @FXML
    private Label dateTimeID;

    public void initialize(String unitName1, String adminID1) {
        this.adminID = adminID1;
        this.unitName = unitName1;
        Date currentDate = new Date();
        dateTimeID.setText(String.valueOf(currentDate));
        setAdminNameOnDashboard(adminID1);
        loadFeedBack(unitName);
    }

    public void setAdminNameOnDashboard(String adminID1) {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT firstName, lastName FROM users WHERE userID = ?";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, adminID1);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String adminName = firstName + " " + lastName;
                    adminNameId.setText(adminName);
                } else {
                    System.out.println("Admin ID not found.");
                }

                statement.close();
                dbConnect.close();
            } else {
                // Handle case when connection is null
                System.out.println("Database Connection Failed");
            }
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    private void loadFeedBack(String unitName) {
        subTitle.setText("Below is a list of feedback from the students taking " +
                unitName + ":");
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT comment, Coscore FROM feedback WHERE unit = ?";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, unitName);

                ResultSet resultSet = statement.executeQuery();

                ObservableList<String> feedbackList = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    String comment = resultSet.getString("comment");
                    int score = resultSet.getInt("Coscore");
                    String feedback = comment + "\t\t" + score;
                    feedbackList.add(feedback);
                }
                feedBackListView.setItems(feedbackList);

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


    @FXML
    public void loadAdminDashboard(ActionEvent event) {
        // Close all open stages
        Stage currentStage = (Stage) subTitle.getScene().getWindow();
        currentStage.close();

        // Implement closing of other stages if needed
        // For example:
    /* Stage otherStage1 = (Stage) someNode.getScene().getWindow();
    otherStage1.close(); */

        // Load the admin home page
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin_Page.fxml"));
//            Parent root1 = fxmlLoader.load();
//            Admin_Controller controller = fxmlLoader.getController();
//            // Pass any necessary data to the admin page controller
//            controller.initializeAttributes(adminID); // Assuming you have such a method
//
//            // Create a new stage for the admin page
//            Stage adminStage = new Stage();
//            adminStage.setTitle("Admin Dashboard");
//            adminStage.setScene(new Scene(root1));
//            adminStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    @FXML
    public void loadCreateReviewStage(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateReview.fxml"));
            Parent root1 = fxmlLoader.load();

            // Create a new stage
            Stage createReviewStage = new Stage();
            createReviewStage.setTitle("Create Review");
            createReviewStage.initModality(Modality.APPLICATION_MODAL);
            createReviewStage.setScene(new Scene(root1));
            createReviewStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void loadChooseLecturerScene(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseLecturer.fxml"));
            Parent root1 = fxmlLoader.load();
            // Create a new stage
            Stage manageLecturersStage = new Stage();
            manageLecturersStage.setTitle("Choose Lecturers");
            manageLecturersStage.initModality(Modality.APPLICATION_MODAL); // This makes the new stage modal
            manageLecturersStage.setScene(new Scene(root1));
            manageLecturersStage.showAndWait(); // Show and wait for the new stage to be closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void viewLecturerCommentsScene(ActionEvent event) {
        openLecturerComments(adminID);
    }

    public void openLecturerComments(String adminID1) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LecturerCommentsHome.fxml"));
            Parent root1 = fxmlLoader.load();
            LectureCommentsHome_Controller controller = fxmlLoader.getController();

            // Pass data to PerformanceReport_Controller
            controller.initialize1(adminID1);

            // Create a new stage
            Stage performanceReportStage = new Stage();
            performanceReportStage.setTitle("Lecturer Feedback Home Page");
            performanceReportStage.initModality(Modality.APPLICATION_MODAL);
            performanceReportStage.setScene(new Scene(root1));
            performanceReportStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
