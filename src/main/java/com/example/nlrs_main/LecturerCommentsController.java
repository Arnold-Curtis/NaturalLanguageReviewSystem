package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

public class LecturerCommentsController extends ChooseUnitController implements Initializable{

    @FXML
    private ListView<String> commentsListView;

    @FXML
    private Label subTitle;

    private String unitName;

    private String lecturer;

    @FXML
    private Label adminNameId;
    @FXML
    private Label dateTimeID;
    @FXML
    private Button logoutButton;
    @FXML
    private ImageView commentsIcon;
    @FXML
    private void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("C:/Users/Alvin Majawa/Desktop/NLRS/src/main/java/com/" +
                "example/nlrs_main/ImagesAndIcons/comments.png");
        commentsIcon.setImage(image);
    }

    public void setUnitName(String unitName, String lecturerID1) {
        this.unitName = unitName;
        this.lecturerID =lecturerID1;
        Date currentDate = new Date();
        dateTimeID.setText(String.valueOf(currentDate));
        setAdminNameOnDashboard(lecturerID1);
        loadLecturerComments(unitName);
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
    public void loadLecturerComments( String unitName) {
        subTitle.setText("Below are comments made on the performance Report " +
                "for the \n" + unitName + " Lecturer:");
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT comment FROM lecfeedback WHERE unit = ?";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, unitName);

                ResultSet resultSet = statement.executeQuery();

                ObservableList<String> feedbackList = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    String comment = resultSet.getString("comment");
                    feedbackList.add(comment);
                }
                commentsListView.setItems(feedbackList);

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
    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturer;
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
    public void viewStudentFeedBackScene(ActionEvent event)
    {
        openStudentFeedback(lecturerID);
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
}