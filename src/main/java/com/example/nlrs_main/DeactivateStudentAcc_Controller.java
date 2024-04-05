package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeactivateStudentAcc_Controller extends Users implements Initializable  {
    @FXML
    private Button closeButton;
    @FXML
    private ImageView deactivationIcon;
    @FXML
    private Label successMessageID;
    @FXML
    private TextField accountID;
    @FXML
    private Label idErrorMessage;
    @FXML
    private PasswordField adminPassword;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label passwordErrorMessage;
    @FXML
    private Button deactivationButton;
    @FXML
    private Button searchButtonid;

    ReadWriteDB connection = new ReadWriteDB();
    Connection dbConnect = connection.getConnection();

   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("C:/Users/Alvin Majawa/Desktop/IS Project Final/src/main/java/com/" +
                "example/nlrs_main/ImagesAndIcons/Account De-activation.png");
        deactivationIcon.setImage(image);
    }
    @FXML
    private void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
    @FXML
    public void searchButtonAction(ActionEvent event){
        try {
            String userID = accountID.getText();
            getStudentDetailsFromDB(userID);
        } catch (Exception e) {
            idErrorMessage.setText("Enter a valid Account ID!!");
        }
    }
    @FXML
    private void deactivationButtonAction(ActionEvent event) {
        try {
            String userID = accountID.getText();
            getStudentDetailsFromDB(userID);
        } catch (Exception e) {
            idErrorMessage.setText("Enter a valid Account ID!!");
        }
    }

    private void verifyAdminPasswords() {
        String adminEnteredPassword = adminPassword.getText();
        String confirmEnteredPassword = confirmPassword.getText();
        if (!adminEnteredPassword.equals(confirmEnteredPassword)) {
            passwordErrorMessage.setText("Your passwords do not match!!");
        } else if (adminEnteredPassword.isEmpty() && confirmEnteredPassword.isBlank()) {
            passwordErrorMessage.setText("Enter Admin Password to De-Activate " + getFirstName() + " " + getLastName()
                    + "'s Account!!");
        } else if (!confirmPasswordWithDB(confirmEnteredPassword)) {
            passwordErrorMessage.setText("Incorrect Administrator Password Entered!!");
        } else {
            accountDeactivation();
        }
    }

    public boolean confirmPasswordWithDB(String password) {
        try {
            String sql = "SELECT COUNT(*) FROM users WHERE userType = 'Admin' AND password = ?";
            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(sql)) {
                preparedStatement.setString(1, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count == 1) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void accountDeactivation() {
        String userID = accountID.getText();
        try {
            if (dbConnect != null) {
                String updateSql = "UPDATE users SET status = 0 WHERE userID = ?";
                try (PreparedStatement updateStatement = dbConnect.prepareStatement(updateSql)) {
                    updateStatement.setString(1, userID);
                    int rowsAffected = updateStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        successMessageID.setText(getFirstName() + " " + getLastName() + "'s " +
                                "Account Deactivated Successfully!!");
                    } else {
                        idErrorMessage.setText("Failed to Deactivate " + getFirstName() + " " +
                                getLastName() + "'s Account");
                    }
                }
            } else {
                idErrorMessage.setText("Database Connection Failure!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            idErrorMessage.setText("Failed to connect to Database!!");
        }
    }

    public void getStudentDetailsFromDB(String userID) {
        try {
            if (dbConnect != null) {
                String sql = "SELECT firstName, lastName, userType, status FROM users WHERE userID = ?";
                try (PreparedStatement preparedStatement = dbConnect.prepareStatement(sql)) {
                    preparedStatement.setString(1, userID);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        setFirstName(resultSet.getString("firstName"));
                        setLastName(resultSet.getString("lastName"));
                        String userType = resultSet.getString("userType");
                        boolean userStatus = resultSet.getBoolean("status");

                        if (!userType.equals("Student")) {
                            idErrorMessage.setText("User: " + userID + " is not a Student!!");
                        } else if (!userStatus) {
                            idErrorMessage.setText("User: " + userID + "'s Account is already De-Activated!!");
                        } else {
                            idErrorMessage.setText("User: " + userID + "'s Account Exists And It's an Active Account!!");
                            verifyAdminPasswords();
                        }
                    } else {
                        idErrorMessage.setText("User: " + userID + " does not exist!!");
                    }
                }
            } else {
                idErrorMessage.setText("Fill in the Required Details!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            idErrorMessage.setText("Failed to connect to Database!!");
        }
    }
}