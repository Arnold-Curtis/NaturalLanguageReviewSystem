package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChooseUnitController implements Initializable {

    @FXML
    private HBox unitButtonsContainer;

    public String lecturerID;


    public String getLecturerID() {
        return lecturerID;
    }

    public String selectedUnitName;

    private String nextAction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void loadUnitButtons() {
        try {
            ReadWriteDB dbConnection = new ReadWriteDB();
            Connection connection = dbConnection.getConnection();

            String query = "SELECT DISTINCT unitName ,userID FROM units WHERE userID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, lecturerID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String unitName = resultSet.getString("unitName");
                String userId = resultSet.getString("userID");
                System.out.println("Unit Name: " + unitName + ", User ID: " + userId);

                Button unitButton = new Button(unitName);
                unitButton.setOnAction(event -> handleUnitSelection(unitName));
                unitButtonsContainer.getChildren().add(unitButton);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleUnitSelection(String unitName) {
        selectedUnitName = unitName;
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) unitButtonsContainer.getScene().getWindow();
        stage.close();
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
        loadUnitButtons();
    }

    public String getSelectedUnitName() {
        return selectedUnitName;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }
}