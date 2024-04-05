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
import java.util.ResourceBundle;

public class StudentsF_Controller implements Initializable {

    @FXML
    private Button unit1;
    @FXML
    private Button unit2;
    @FXML
    private Button unit3;
    @FXML
    private Button unit4;
    @FXML
    private Button unit5;
    @FXML
    private Button unit6;

    private ReadWriteDB con;

    private String studentID;
    @FXML
    private Button logoutButton;
    @FXML
    private ImageView nlp1;
    @FXML
    private void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("C://Users//Alvin Majawa//Desktop//NLRS//src//main//java//com//example//nlrs_main" +
                "//ImagesAndIcons//icon.png");
        nlp1.setImage(image);
        con = new ReadWriteDB();
        setUnitButtonsFromDB();
    }

    private void setUnitButtonsFromDB() {
        try {
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT unitName FROM units";
                PreparedStatement statement = dbConnect.prepareStatement(query);

                ResultSet resultSet = statement.executeQuery();
                int buttonIndex = 1;
                while (resultSet.next()) {
                    String unitName = resultSet.getString("unitName");
                    Button unitButton = getButtonByIndex(buttonIndex);
                    if (unitButton != null) {
                        unitButton.setText(unitName);
                        unitButton.setOnAction(this::loadAnswerReviewsScene);
                    }
                    buttonIndex++;
                }
                resultSet.close();
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

    private Button getButtonByIndex(int index) {
        switch (index) {
            case 1:
                return unit1;
            case 2:
                return unit2;
            case 3:
                return unit3;
            case 4:
                return unit4;
            case 5:
                return unit5;
            case 6:
                return unit6;
            default:
                return null;
        }
    }

    @FXML
    public void loadAnswerReviewsScene(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AnswerReview.fxml"));
            Parent root1 = fxmlLoader.load();

            // Get the selected unit name from the button text
            Button clickedButton = (Button) event.getSource();
            String selectedUnitName = clickedButton.getText();

            // Create a new stage
            Stage answerReviewsStage = new Stage();
            answerReviewsStage.setTitle("Answer Review");
            answerReviewsStage.initModality(Modality.APPLICATION_MODAL); // This makes the new stage modal
            Scene scene = new Scene(root1);
            answerReviewsStage.setScene(scene);

            // Initialize the AnswerReview_Controller with the selected unit name
            AnswerReview_Controller controller = fxmlLoader.getController();
            controller.setStudentID(this.studentID);
            controller.initialize(selectedUnitName);

            answerReviewsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStudentID(String userID) {
        this.studentID = userID;
    }
}