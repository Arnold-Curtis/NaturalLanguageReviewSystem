package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Login_Controller extends ReadWriteDB implements Initializable {
    @FXML
    private ImageView loginIcon;
    @FXML
    ChoiceBox<String> accountTypeChoiceBox;

    @FXML
    private TextField userIDtf;

    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField passwordtf;

    @FXML
    private Label messageLabel;

    private Runnable onLoginHandler;

    private String studentID;
    private String loggedInUserID;


    //I have commented out this part. So it doesn't give any of us problems.
    //But it works. so don't worry about the icons and don't change anything on it.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("C://Users//Alvin Majawa//Desktop//IS Project Final//src//main//java//com//example//nlrs_main//ImagesAndIcons//loginIcon.png/");
        loginIcon.setImage(image);
    }

    @FXML
    private void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }


    @FXML
    private void loginButton(ActionEvent event) {
        String userID = userIDtf.getText();
        String password = passwordtf.getText();

        try {
            if (!userID.isBlank() && !password.isBlank()) {
                if (!accountTypeChoiceBox.getValue().isBlank()) {
                    ReadWriteDB dbReader = new ReadWriteDB();
                    boolean loginSuccess = dbReader.getLoginDetailsFromDB(userID, password);
                    if (loginSuccess) {
                        setUserID(userID);
                        setLoggedInUserID(userID);
                        if (onLoginHandler != null) {
                            setLoggedInUserID(userID);
                            onLoginHandler.run();
                        }
                        if (accountTypeChoiceBox.getValue().equalsIgnoreCase("Student")) {
                            Users user = new Users();
                            user.setStudentID(userID); // Set the studentID field in the Users class
                            setStudentID(user.getStudentID()); // Set the studentID in the Login_Controller class
                        }
                        if (accountTypeChoiceBox.getValue().equalsIgnoreCase("Lecturer")) {
                            setLoggedInUserID(userID);
                        }
                    } else {
                        messageLabel.setText("Incorrect credentials. Please try again.");
                    }
                } else {
                    messageLabel.setText("Select Account Type!");
                }
            } else {
                messageLabel.setText("Fill in the Details");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid userID. Please enter a valid integer.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserAccountType(String userID) throws Exception {
        try {
            Connection dbConnect = getConnection();

            String sql = "SELECT userType FROM users WHERE userID = ?";
            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(sql)) {
                preparedStatement.setString(1, userID);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("userType");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setOnLoginHandler(Runnable handler) {
        this.onLoginHandler = handler;
    }

    public void setLoggedInUserID(String userID) {
        this.loggedInUserID = userID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

}

