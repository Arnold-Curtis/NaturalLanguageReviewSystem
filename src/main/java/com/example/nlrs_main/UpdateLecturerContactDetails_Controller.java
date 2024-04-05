package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateLecturerContactDetails_Controller {
    private String lecturerID;

    @FXML
    private TextField oldEmailTF;
    @FXML
    private TextField oldPhoneNumberTF;
    @FXML
    private TextField newPhoneNumberTF;
    @FXML
    private TextField newEmailTF;
    @FXML
    private Label successMessageLabel;
    @FXML
    private Label messageFailureLabel;
    @FXML
    private Button updateContactDetails;

    public void initialize(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    @FXML
    private void updateContactDetails() {
        String newEmail = newEmailTF.getText();
        String newPhoneNumber = newPhoneNumberTF.getText();

        if (!validateOldDetails()) {
            messageFailureLabel.setText("Old email or phone number is incorrect.");
            //messageFailureLabel.setText(" ");
        } else {

            try {
                ReadWriteDB con = new ReadWriteDB();
                Connection dbConnect = con.getConnection();
                if (dbConnect != null) {
                    String query = "UPDATE users SET email = ?, phoneNumber = ? WHERE userID = ?";
                    PreparedStatement statement = dbConnect.prepareStatement(query);
                    statement.setString(1, newEmail);
                    statement.setString(2, newPhoneNumber);
                    statement.setString(3, lecturerID);

                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        successMessageLabel.setText("Contact details updated successfully.");
                        //successMessageLabel.setText(" ");
                    } else {
                        messageFailureLabel.setText("Failed to update contact details.");
                        //messageFailureLabel.setText(" ");
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
    }

    private boolean validateOldDetails() {
        String oldEmail = oldEmailTF.getText();
        String oldPhoneNumber = oldPhoneNumberTF.getText();

        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT email, phoneNumber FROM users WHERE userID = ?";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, lecturerID);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String storedEmail = resultSet.getString("email");
                    String storedPhoneNumber = resultSet.getString("phoneNumber");
                    return oldEmail.equals(storedEmail) && oldPhoneNumber.equals(storedPhoneNumber);
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
