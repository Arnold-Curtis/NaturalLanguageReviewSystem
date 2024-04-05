package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class LecturerRegistration_Controller extends Login_Controller implements Initializable {

    @FXML
    private ImageView registrationImage;

    @FXML
    private Button cancelButton;

    @FXML
    private Button lecturerRegistrationButtonid;

    @FXML
    private PasswordField defaultPasswordTF;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private TextField courseNameTF;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField countryTF;

    @FXML
    private TextField dateOfBirthTF;
    @FXML
    private TextField unitNameTF;

    @FXML
    private Label registrationSuccessMessageLabel;

    @FXML
    private Label registrationMessageFailureLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image pageIcon = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//" +
                "example//nlrs_main//ImagesAndIcons//registrationImage.png/");
        registrationImage.setImage(pageIcon);
    }


    @FXML
    private void cancelButton2Action(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registrationButtonAction(ActionEvent event) {
        if (validateFields()) {
            avoidDuplication();
        } else {
            registrationMessageFailureLabel.setText("Please fill up the registration form.");
        }
    }

    private boolean validateFields() {
        return !firstNameTF.getText().isEmpty() &&
                !lastNameTF.getText().isEmpty() &&
                !dateOfBirthTF.getText().isEmpty() &&
                !emailTF.getText().isEmpty() &&
                !countryTF.getText().isEmpty() &&
                !phoneNumberTF.getText().isEmpty() &&
                !unitNameTF.getText().isEmpty();
    }

    private void clearFields() {
        firstNameTF.clear();
        lastNameTF.clear();
        courseNameTF.clear();
        phoneNumberTF.clear();
        emailTF.clear();
        countryTF.clear();
        dateOfBirthTF.clear();
        unitNameTF.clear();
    }

    private void lecturerRegistration() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();

            String userID = "L"+firstNameTF.getText();

            if (dbConnect != null) {
                String insertDbFields = "INSERT INTO users (userID, userType, userName, password, firstName, lastName," +
                        " dateOfBirth, email, phoneNumber, status, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)";

                PreparedStatement statement = dbConnect.prepareStatement(insertDbFields);
                statement.setString(1, userID);
                statement.setString(2, "Lecturer");
                statement.setString(3, firstNameTF.getText() + lastNameTF.getText());
                statement.setString(4, "Cuba2030");
                statement.setString(5, firstNameTF.getText());
                statement.setString(6, lastNameTF.getText());
                statement.setString(7, dateOfBirthTF.getText());
                statement.setString(8, emailTF.getText());
                statement.setString(9, phoneNumberTF.getText());
                statement.setBoolean(10, true);
                statement.setString(11,countryTF.getText());

                String insertDbFields2 = "INSERT INTO units (userID, unitName) VALUES (?, ?)";

                PreparedStatement statement2 = dbConnect.prepareStatement(insertDbFields2);
                statement2.setString(1, userID);
                statement2.setString(2, unitNameTF.getText());


                int rowsInserted = statement.executeUpdate() + statement2.executeUpdate();
                if (rowsInserted > 0) {
                    registrationSuccessMessageLabel.setText("Lecturer has been successfully Registered!");
                    //clearFields();
                } else {
                    registrationMessageFailureLabel.setText("Registration failed. Please try again.");
                }
            } else {
                registrationMessageFailureLabel.setText("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            registrationMessageFailureLabel.setText("Registration failed due to a database error.");
        } catch (Exception e) {
            e.printStackTrace();
            registrationMessageFailureLabel.setText("An unexpected error occurred during registration.");
        }
    }

    private void avoidDuplication() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();

            if (dbConnect != null) {
                String sqlQuery = "SELECT * FROM users WHERE userType = ? AND userName = ?";
                PreparedStatement statement = dbConnect.prepareStatement(sqlQuery);
                statement.setString(1, "Lecturer");
                statement.setString(2, firstNameTF.getText() + lastNameTF.getText());

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    registrationMessageFailureLabel.setText("User already exists!");
                } else {
                    lecturerRegistration();
                }
            } else {
                registrationMessageFailureLabel.setText("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            registrationMessageFailureLabel.setText("Database error occurred while checking for duplicates.");
        } catch (Exception e) {
            e.printStackTrace();
            registrationMessageFailureLabel.setText("An unexpected error occurred while checking for duplicates.");
        }
    }

}