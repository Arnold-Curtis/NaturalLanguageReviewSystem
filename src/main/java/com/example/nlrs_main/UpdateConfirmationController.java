package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateConfirmationController {
    private String lecturerID;

    @FXML
    private Label successMessageLabel;

    @FXML
    private Label messageFailureLabel;

    @FXML
    private Button updateButton;

    @FXML
    private PasswordField confirmPasswordField;

    public void initialize(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    @FXML
    private void handleUpdateButtonClick() {
        String enteredPassword = confirmPasswordField.getText();

        if (validatePassword(enteredPassword)) {
            successMessageLabel.setText("Password correct. Proceeding with update.");
            // Now, proceed with the update process
            Stage stage = (Stage) updateButton.getScene().getWindow();
            UpdateAllLectureDetails_Controller parentController = (UpdateAllLectureDetails_Controller) stage.getUserData();
            parentController.updateAllDetails();
        } else {
            messageFailureLabel.setText("Incorrect password. Please try again.");
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
