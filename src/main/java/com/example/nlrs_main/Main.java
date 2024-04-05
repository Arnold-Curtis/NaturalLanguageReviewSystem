package com.example.nlrs_main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Main extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent loginRoot = fxmlLoader.load();
        Scene loginScene = new Scene(loginRoot, 594, 400);


        primaryStage.setTitle("Login Page");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        Login_Controller loginController = fxmlLoader.getController();
        loginController.setOnLoginHandler(() -> handleLogin(loginController));
    }

    private void handleLogin(Login_Controller loginController) {
        String userID = loginController.getUserID();
        try {
            String accountType = loginController.getUserAccountType(userID);
            if (accountType != null) {
                String selectedAccountType = loginController.accountTypeChoiceBox.getValue();
                if (selectedAccountType.equalsIgnoreCase(accountType)) {
                    switch (accountType) {
                        // Existing cases
                        case "Admin":
                            loadAdminScene(userID);
                            break;
                        case "Student":
                            loadStudentScene(userID);
                            break;
                        case "Lecturer":
                            loadLecturerScene(userID);
                            break;
                    }
                } else {
                    System.out.println("Invalid account type selected");
                }
            } else {
                System.out.println("Failed to retrieve account type from the database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadAdminScene(String userID) {
        loadAdminScene1(userID);
    }

    public void loadAdminScene1(String adminID){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin_Page.fxml"));
            Parent root = loader.load();
            Admin_Controller controller = loader.getController();
            controller.initializeAttributes(adminID); // Set the lecturer's ID
            //controller.setUnitFromDB(userID); // Retrieve and set unit names
            primaryStage.setScene(new Scene(root, 601, 401));
            primaryStage.setTitle("Admin Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentScene(String userID) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Students_fPage.fxml"));
            Parent root = loader.load();
            StudentsF_Controller controller = loader.getController();
            controller.setStudentID(userID);
            primaryStage.setScene(new Scene(root, 600, 402));
            primaryStage.setTitle("Student Dashboard");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLecturerScene(String userID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LecturerWindow.fxml"));
            Parent root = loader.load();
            LecturerWindow_Controller controller = loader.getController();
            controller.setLecturerID(userID); // Set the lecturer's ID
            controller.setUnitFromDB(userID); // Retrieve and set unit names
            primaryStage.setScene(new Scene(root, 507, 400));
            primaryStage.setTitle("Lecturer Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
