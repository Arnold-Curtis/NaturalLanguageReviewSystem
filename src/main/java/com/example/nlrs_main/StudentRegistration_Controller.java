package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
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
import java.util.ResourceBundle;

//This is poor but do not remove the commented out end of the login_Controller className
//It works together with the methods that display images/Icons on our windows.
public class StudentRegistration_Controller extends Login_Controller implements Initializable {

    @FXML
    private ImageView registrationImage1;

    @FXML
    private Button cancelButton;

    @FXML
    private Button studentRegistrationButtonid;

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
    private Label registrationSuccessMessageLabel;

    @FXML
    private Label registrationMessageFailureLabel;

    @FXML
    private void cancelButton3Action(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registrationButton1Action(ActionEvent event) {
        avoidDuplication();
    }

    public void studentRegistration() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();

            if (dbConnect != null) {
                String firstName = firstNameTF.getText();
                String userID = "S"+firstNameTF.getText();
                String lastName = lastNameTF.getText();
                String dateOfBirth = dateOfBirthTF.getText();
                String email = emailTF.getText();
                String country = countryTF.getText();
                String phoneNumber = phoneNumberTF.getText();
                //String courseName = courseNameTF.getText();
                setPassword("Cuba2030");
                String defaultPassword = getPassword();
                String userName = firstName + lastName;
                setUserType("Student");
                String userType = getUserType();
                setUserStatus(true);
                boolean userStatus = getUserStatus();

                String insertDbFields = "INSERT INTO users (userID, userType, userName, password, firstName, lastName, dateOfBirth, " +
                        "email, phoneNumber, country, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

                PreparedStatement statement = dbConnect.prepareStatement(insertDbFields);
                statement.setString(1,userID);
                statement.setString(2, userType);
                statement.setString(3, userName);
                statement.setString(4, defaultPassword);
                statement.setString(5, firstName);
                statement.setString(6, lastName);
                statement.setString(7, dateOfBirth);
                statement.setString(8, email);
                statement.setString(9, phoneNumber);
                statement.setString(10, country);
                //statement.setString(10, courseName);
                statement.setBoolean(11, userStatus);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    registrationSuccessMessageLabel.setText(userType + " has been successfully Registered!");
                } else {
                    registrationMessageFailureLabel.setText(userType + " Account Registration failed. Please try again.");
                }
            } else {
                registrationMessageFailureLabel.setText("Failed to connect to the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrationMessageFailureLabel.setText("Complete the form!!");
        }
    }

    public void avoidDuplication(){
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();

            if (dbConnect != null) {

                String firstName = firstNameTF.getText();
                String userID = "S"+firstNameTF.getText();
                String lastName = lastNameTF.getText();
                String dateOfBirth = dateOfBirthTF.getText();
                String email = emailTF.getText();
                String country = countryTF.getText();
                String phoneNumber = phoneNumberTF.getText();
                //String courseName = courseNameTF.getText();
                setPassword("Cuba2030");
                String defaultPassword = getPassword();
                String userName = firstName + lastName;
                setUserType("Student");
                String userType = getUserType();
                setUserStatus(true);
                boolean userStatus = getUserStatus();

                String sqlQuery = "SELECT * FROM users WHERE userType = ? AND userID = ? AND userName = ? AND password = ? AND " +
                        "firstName = ? AND lastName = ? AND dateOfBirth = ? AND email = ? AND phoneNumber = ? AND country = ? AND status = ?";
                PreparedStatement statement = dbConnect.prepareStatement(sqlQuery);
                statement.setString(1, userType);
                statement.setString(2, userID);
                statement.setString(3, userName);
                statement.setString(4, defaultPassword); // Assuming this is the password
                statement.setString(5, firstName);
                statement.setString(6, lastName);
                statement.setString(7, dateOfBirth); // Assuming dateOfBirth is in the format 'YYYY-MM-DD'
                statement.setString(8, email);
                statement.setString(9, phoneNumber);
                statement.setString(10, country);
                statement.setBoolean(11, userStatus);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    registrationMessageFailureLabel.setText("User: " + firstName + " " + lastName + " already exists!!");
                } else {
                    studentRegistration();
                }
            } else {
                registrationMessageFailureLabel.setText("Failed to connect to the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrationMessageFailureLabel.setText("Please Fill-Up the Registration Form!");
        }
    }

    //I have commented out this part. So it doesn't give any of us problems.
    //But it works. so don't worry about the icons and don't change anything on it.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//" +
                "example//nlrs_main//ImagesAndIcons//registrationImage.png/");
        registrationImage1.setImage(image);
    }
}
