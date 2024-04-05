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

public class ManageLecturers_Controller implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private ImageView manageLecturersIcon;
    @FXML
    private ImageView registrationImage;
    @FXML
    private ImageView deactivationImage1;
    @FXML
    private Label adminNameId;
    @FXML
    private Label dateTimeID;
    private String adminID;

    public void initializeAttributes(String adminID1){
        this.adminID = adminID1;
        setAdminNameOnDashboard(adminID1);
    }

    @FXML
    private void manageLecturerCancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image studentsIcon = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//" +
                "example//nlrs_main//ImagesAndIcons//Lecturers.png/");
        manageLecturersIcon.setImage(studentsIcon);

        Image registrationIcon = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//" +
                "example//nlrs_main//ImagesAndIcons//registrationImage.png/");
        registrationImage.setImage(registrationIcon);

        Image deactivationIcon = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//" +
                "example//nlrs_main//ImagesAndIcons//Account De-Activation.png/");
        deactivationImage1.setImage(deactivationIcon);
        Admin_Controller controller = new Admin_Controller();
        controller.setAdminNameOnDashboard(adminID);
        Date currentDate = new Date();
        dateTimeID.setText(String.valueOf(currentDate));
        setAdminNameOnDashboard(adminID);
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
    public void loadRegisterLecturerScene(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LecturerRegistration.fxml"));
            Parent root1 = fxmlLoader.load();
            // Create a new stage
            Stage lecturerRegistrationStage = new Stage();
            lecturerRegistrationStage.setTitle("Lecturer Account Registration");
            lecturerRegistrationStage.initModality(Modality.APPLICATION_MODAL);
            lecturerRegistrationStage.setScene(new Scene(root1));
            lecturerRegistrationStage.showAndWait();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void loadDeActivateLecturerAccScene(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeactivateLecturerAcc.fxml"));
            Parent root1 = fxmlLoader.load();
            // Create a new stage
            Stage lecturerAccountDeactivationStage = new Stage();
            lecturerAccountDeactivationStage.setTitle("Lecturer Account De-Activation");
            lecturerAccountDeactivationStage.initModality(Modality.APPLICATION_MODAL);
            lecturerAccountDeactivationStage.setScene(new Scene(root1));
            lecturerAccountDeactivationStage.showAndWait();
        } catch (IOException e) {

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
    @FXML
    public void loadAdminDashboard(ActionEvent event) {
        // Close all open stages
        Stage currentStage = (Stage) adminNameId.getScene().getWindow();
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
}
