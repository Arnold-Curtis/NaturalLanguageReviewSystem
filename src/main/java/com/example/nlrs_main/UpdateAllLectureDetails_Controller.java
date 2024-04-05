package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UpdateAllLectureDetails_Controller {
    private String lecturerID;

    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField countryTF;
    @FXML
    private TextField phoneNumberTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField dateOfBirthTF;
    @FXML
    private Button updateAllDetails;
    @FXML
    private Label successMessageLabel;
    @FXML
    private Label messageFailureLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void initialize(String lecturerID1) {
        this.lecturerID = lecturerID1;
    }


    public void updateAllDetails() {
        // Proceed with updating details if password is correct
        String newFirstName = firstNameTF.getText();
        String newLastName = lastNameTF.getText();
        String newCountry = countryTF.getText();
        String newPhoneNumber = phoneNumberTF.getText();
        String newEmail = emailTF.getText();
        String newDateOfBirth = dateOfBirthTF.getText();

        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "UPDATE users SET firstName = ?, lastName = ?, country = ?, phoneNumber = ?, email = ?, dateOfBirth = ? WHERE userID = ?";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, newFirstName);
                statement.setString(2, newLastName);
                statement.setString(3, newCountry);
                statement.setString(4, newPhoneNumber);
                statement.setString(5, newEmail);
                statement.setString(6, newDateOfBirth);
                statement.setString(7, lecturerID);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    successMessageLabel.setText("Details updated successfully.");

                } else {
                    messageFailureLabel.setText("Failed to update details.");
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
    @FXML
    private void promptForPassword() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updateDetails.fxml"));
            Parent root1 = fxmlLoader.load();
            UpdateConfirmationController controller = new UpdateConfirmationController();
            // Pass data to UpdateAllLectureDetails_Controller
            controller.initialize(lecturerID);

            // Create a new stage
            Stage updateAllUserDetailsStage = new Stage();
            updateAllUserDetailsStage.setTitle("Update All Lecture Details Page");
            updateAllUserDetailsStage.initModality(Modality.APPLICATION_MODAL);
            updateAllUserDetailsStage.setScene(new Scene(root1));
            updateAllUserDetailsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean validatePassword(String enteredPassword) {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT password FROM users WHERE userID = ?";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, lecturerID);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    return enteredPassword.equals(storedPassword);
                } else {
                    return false;
                }
            } else {
                System.out.println("Database Connection Failed");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
            return false;
        }
    }
}
