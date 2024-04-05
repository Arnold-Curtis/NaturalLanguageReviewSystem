package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class Admin_Controller extends Login_Page implements Initializable {
    private String adminID;

    @FXML
    private Button logoutButton;
    @FXML
    private ImageView adminPageIcon;
    @FXML
    private Label adminNameId;
    @FXML
    private Label dateTimeID;
    @FXML
    private ImageView manageStudentsIcon;

    @FXML
    private ImageView manageLecturerIcon;
    @FXML
    private ImageView performanceReportIcon;
    public void initializeAttributes(String adminID1) {
        this.adminID = adminID1;
        Date currentDate = new Date();
        dateTimeID.setText(String.valueOf(currentDate));
        setAdminNameOnDashboard(adminID1);
    }
    @FXML
    private void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image pageIcon = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//" +
                "example//nlrs_main//ImagesAndIcons//AdminHomeIcon.png/");
        adminPageIcon.setImage(pageIcon);

        Image studentsIcon = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//" +
                "example//nlrs_main//ImagesAndIcons//Manage Students.png/");
        manageStudentsIcon.setImage(studentsIcon);

        Image lecturesIcon = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//" +
                "example//nlrs_main//ImagesAndIcons//Lecturers.png/");
        manageLecturerIcon.setImage(lecturesIcon);

        Image reportIcon = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//" +
                "example//nlrs_main//ImagesAndIcons//Performance Report.png/");
        performanceReportIcon.setImage(reportIcon);

        Date currentDate = new Date();
        dateTimeID.setText(String.valueOf(currentDate));

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
    @FXML
    public void viewStudentFeedBackScene(ActionEvent event)
    {
        openStudentFeedback(adminID);
    }
    public void openStudentFeedback(String adminID1){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentsFeedBack.fxml"));
            Parent root1 = fxmlLoader.load();
            StudentsFeedBackHomeWindow_Controller controller = fxmlLoader.getController();

            // Pass data to PerformanceReport_Controller
            controller.initialize(adminID1);

            // Create a new stage
            Stage performanceReportStage = new Stage();
            performanceReportStage.setTitle("Student Feedback Home Page");
            performanceReportStage.initModality(Modality.APPLICATION_MODAL);
            performanceReportStage.setScene(new Scene(root1));
            performanceReportStage.showAndWait();
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

    @FXML
    public void loadManageStudentsScene(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Manage_Students.fxml"));
            Parent root1 = fxmlLoader.load();
            ManageStudents_Controller controller = fxmlLoader.getController();
            controller.initializeAttributes(this.adminID);
            // Create a new stage
            Stage manageStudentsStage = new Stage();
            manageStudentsStage.setTitle("Manage Students");
            manageStudentsStage.initModality(Modality.APPLICATION_MODAL); // This makes the new stage modal
            manageStudentsStage.setScene(new Scene(root1));
            manageStudentsStage.showAndWait(); // Show and wait for the new stage to be closed
        } catch (IOException e) {
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
    public void loadManageLecturersScene(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Manage_Lecturers.fxml"));
            Parent root1 = fxmlLoader.load();
            ManageLecturers_Controller controller = fxmlLoader.getController();
            controller.initializeAttributes(this.adminID);
            // Create a new stage
            Stage manageLecturersStage = new Stage();
            manageLecturersStage.setTitle("Manage Lecturers");
            manageLecturersStage.initModality(Modality.APPLICATION_MODAL);
            manageLecturersStage.setScene(new Scene(root1));
            manageLecturersStage.showAndWait();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
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
}
