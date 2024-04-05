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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChooseLecturerController implements Initializable {

    @FXML
    private HBox lecturerButtonsContainer;
    @FXML
    private Button logoutButton;

    private String adminID;
    private boolean forPerformanceReport;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLecturerButtons();
    }

    private void loadLecturerButtons() {
        try {
            ReadWriteDB dbConnection = new ReadWriteDB();
            Connection connection = dbConnection.getConnection();

            String query = "SELECT userID, firstName, lastName FROM users WHERE userType = 'Lecturer'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String lecturerID = resultSet.getString("userID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String lecturerName = firstName + " " + lastName;

                Button lecturerButton = new Button(lecturerName);
                lecturerButton.setOnAction(event -> openLecturerPerformanceReport(lecturerID, ((Button) event.getSource()).getId()));
                lecturerButton.setId(lecturerID); // Set the ID of the button to lecturerID
                lecturerButtonsContainer.getChildren().add(lecturerButton);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void openLecturerPerformanceReport(String lecturerID, String id) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseUnit.fxml"));
            Parent root = fxmlLoader.load();

            ChooseUnitController controller = fxmlLoader.getController();
            controller.setLecturerID(lecturerID); // Set the lecturerID

            Stage chooseUnitStage = new Stage();
            chooseUnitStage.initModality(Modality.APPLICATION_MODAL);
            chooseUnitStage.setTitle("Choose Unit");
            chooseUnitStage.setScene(new Scene(root));
            chooseUnitStage.showAndWait();

            String selectedUnitName = controller.getSelectedUnitName();
            if (selectedUnitName != null) {
                    loadPerformanceReportScene(lecturerID, selectedUnitName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void loadLecturerCommentsScene(String lecturerID, String selectedUnitName)
//    {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LecturerComments.fxml"));
//            Parent root = fxmlLoader.load();
//
//            LecturerCommentsController controller = fxmlLoader.getController();
//            // Pass necessary data to the controller if needed
//            controller.setUnitName(lecturerID, selectedUnitName);
//
//            Stage lecturersCommentsStage = new Stage();
//            lecturersCommentsStage.initModality(Modality.APPLICATION_MODAL);
//            lecturersCommentsStage.setTitle("Lecturer Comments");
//            lecturersCommentsStage.setScene(new Scene(root));
//            lecturersCommentsStage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    @FXML
    private void handleLogout(ActionEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }


    private void loadPerformanceReportScene(String lecturerID, String unitName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PerformanceReport.fxml"));
            Parent root = fxmlLoader.load();

            PerformanceReport_Controller controller = fxmlLoader.getController();
            controller.initialize(lecturerID, unitName);

            Stage performanceReportStage = new Stage();
            performanceReportStage.initModality(Modality.APPLICATION_MODAL);
            performanceReportStage.setTitle("Lecturer Performance Report");
            performanceReportStage.setScene(new Scene(root));
            performanceReportStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();


        }

}

    public void setForPerformanceReport(boolean forPerformanceReport) {
        this.forPerformanceReport = forPerformanceReport;
    }

}
