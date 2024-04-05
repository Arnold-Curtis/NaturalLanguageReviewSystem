package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import edu.stanford.nlp.ling.CoreAnnotations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.events.Event;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class StudentsFeedBackHomeWindow_Controller {
    private String adminID;
    @FXML
    private Button unit1;
    @FXML
    private Button unit2;
    @FXML
    private Button unit3;
    @FXML
    private Button unit4;
    @FXML
    private Button unit5;
    @FXML
    private Button unit6;
    @FXML
    private Label adminNameId;
    @FXML
    private Label dateTimeID;
    public void initialize(String adminID1) {
        this.adminID = adminID1;
        loadUnitNamesToButtons();
        Date currentDate = new Date();
        dateTimeID.setText(String.valueOf(currentDate));
        setAdminNameOnDashboard(adminID1);
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
    public void loadUnitNamesToButtons(){
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT unitName FROM units";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                unit1.setText("Unit Name");
                unit2.setText("Unit Name");
                unit3.setText("Unit Name");
                unit4.setText("Unit Name");
                unit5.setText("Unit Name");
                unit6.setText("Unit Name");


                int i = 1;
                while (resultSet.next() && i <= 6) {
                    String unitName = resultSet.getString("unitName");
                    switch (i) {
                        case 1:
                            unit1.setText(unitName);
                            break;
                        case 2:
                            unit2.setText(unitName);
                            break;
                        case 3:
                            unit3.setText(unitName);
                            break;
                        case 4:
                            unit4.setText(unitName);
                            break;
                        case 5:
                            unit5.setText(unitName);
                            break;
                        case 6:
                            unit6.setText(unitName);
                            break;
                    }
                    i++;
                }

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
    @FXML
    private void loadUnitFeedback(ActionEvent event){
        String unitName = ((Button) event.getSource()).getText();
        openUnitFeedBack(unitName, adminID);
    }
    private void openUnitFeedBack(String unitName, String adminID) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentsFeedBackListWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            DisplayFeedBack_Controller controller = fxmlLoader.getController();

            // Pass data to PerformanceReport_Controller
            controller.initialize(unitName,adminID);

            // Create a new stage
            Stage performanceReportStage = new Stage();
            performanceReportStage.setTitle("Students FeedBack");
            performanceReportStage.initModality(Modality.APPLICATION_MODAL);
            performanceReportStage.setScene(new Scene(root1));
            performanceReportStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadAdminDashboard(ActionEvent event) {
        // Close all open stages
        Stage currentStage = (Stage) unit1.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void loadCreateReviewStage(ActionEvent event) {

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateReview.fxml"));
            Parent root1 = fxmlLoader.load();

            // Create a new stage
            Stage createReviewStage = new Stage();
            createReviewStage.setTitle("Create Review");
            createReviewStage.initModality(Modality.APPLICATION_MODAL);
            createReviewStage.setScene(new Scene(root1));
            createReviewStage.showAndWait();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void loadChooseLecturerScene(ActionEvent event) {
        try
        {
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
    public void viewLecturerCommentsScene(ActionEvent event)
    {
        openLecturerComments(adminID);
    }
    public void openLecturerComments(String adminID1){
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
