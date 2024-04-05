package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class updatePassword_Controller {
    private String lecturerID;

    @FXML
    private Label SuccessMessageLabel;

    @FXML
    private Label MessageFailureLabel;

    @FXML
    private Button updatePasswordBT;

    @FXML
    private TextField newPasswordTF;

    @FXML
    private PasswordField confirmPasswordTF;

    @FXML
    private TextField currentPasswordTF;

    public void initialize(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    @FXML
    private void clearLabel(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setText(" ");
    }
    @FXML
    private void updatePassword(ActionEvent event) {
        String currentPassword = currentPasswordTF.getText();
        String newPassword = newPasswordTF.getText();
        String confirmPassword = confirmPasswordTF.getText();

        if (!newPassword.equals(confirmPassword)) {
            MessageFailureLabel.setText("New password and confirm password do not match.");
            return;
        }

        if (!validateCurrentPassword(currentPassword)) {
            MessageFailureLabel.setText("Incorrect current password.");
            return;
        }

        if (updatePasswordInDatabase(newPassword)) {
            SuccessMessageLabel.setText("Password updated successfully.");
            currentPasswordTF.clear();
            newPasswordTF.clear();
            confirmPasswordTF.clear();
        } else {
            MessageFailureLabel.setText("Failed to update password. Please try again.");
        }
    }

    private boolean validateCurrentPassword(String currentPassword) {
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
                    return currentPassword.equals(storedPassword);
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

    private boolean updatePasswordInDatabase(String newPassword) {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "UPDATE users SET password = ? WHERE userID = ?";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, newPassword);
                statement.setString(2, lecturerID);

                int rowsUpdated = statement.executeUpdate();
                statement.close();
                dbConnect.close();
                return rowsUpdated > 0;
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
