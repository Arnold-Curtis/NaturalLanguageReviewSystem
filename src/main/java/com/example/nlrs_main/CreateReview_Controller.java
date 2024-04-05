package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateReview_Controller implements Initializable {

    public Label registrationSuccessMessageLabel;
    public Button submitCreatedReviewButton;
    public Label registrationMessageFailureLabel;
    @FXML
    private TextField questionTextField;

    @FXML
    private Button addQuestionButton;
    @FXML
    private Button logoutButton;
    @FXML
    private ImageView icon;

    @FXML
    private ListView<String> questionsListView;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    private ObservableList<String> questions = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> CRUnit;

    @FXML
    private ChoiceBox<String> CRLec;

    @FXML
    private TextField reviewTitle;

    @FXML
    private ObservableList<String> lecturerList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> unitList = FXCollections.observableArrayList();
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Image image = new Image("C:/Users/Alvin Majawa/Desktop/IS Project Final/src/main/java/com/" +
//                "example/nlrs_main/ImagesAndIcons/formIcon.png");
//        icon.setImage(image);
//    }

    @FXML
    private void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("C:/Users/Alvin Majawa/Desktop/IS Project Final/src/main/java/com/" +
                "example/nlrs_main/ImagesAndIcons/formIcon.png");
        icon.setImage(image);
        questionsListView.setItems(questions);

        // Add categories to the categoryChoiceBox
        categoryChoiceBox.getItems().addAll(
                "Knowledge and Expertise",
                "Teaching Style and Delivery",
                "Clarity of Explanations",
                "Engagement and Interaction with Students",
                "Responsiveness and Availability",
                "Course Organization and Structure",
                "Fairness and Objectivity in Grading",
                "Use of Visual Aids and Technology",
                "Providing Relevant and Up-to-Date Content",
                "Encouraging Critical Thinking and Discussion",
                "Providing Constructive Feedback",
                "Punctuality and Time Management",
                "Respect for Diverse Perspectives",
                "Enthusiasm and Passion for Teaching",
                "Overall Effectiveness in Facilitating Learning"
        );

        fetchLecturers();
        fetchUnits();


        CRLec.setItems(lecturerList);
        CRUnit.setItems(unitList);

        // Add event handlers
        categoryChoiceBox.setOnAction(event -> handleCategorySelection());
        addQuestionButton.setOnAction(event -> addQuestion());
    }

    private void handleCategorySelection() {
        String selectedCategory = categoryChoiceBox.getValue();
        if (selectedCategory != null) {
            questionTextField.clear();
            questionTextField.setPromptText("Enter question for " + selectedCategory);
        }
    }

    private void addQuestion() {
        String selectedCategory = categoryChoiceBox.getValue();
        if (selectedCategory != null) {
            String question = questionTextField.getText().trim();
            if (!question.isEmpty()) {
                questions.add(selectedCategory + ": " + question);
                questionTextField.clear();
            }
        }
    }

    public void saveFormDetails() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();

            if (dbConnect != null) {
                String reviewTitleText = this.reviewTitle.getText();
                String lecturerName = CRLec.getValue();

                // Fetch Lecturer UserID based on Name
                String getUserIdQuery = "SELECT userID FROM users WHERE CONCAT(firstName, ' ', lastName) = ?";
                PreparedStatement getUserIdStatement = dbConnect.prepareStatement(getUserIdQuery);
                getUserIdStatement.setString(1, lecturerName);
                ResultSet userIdResultSet = getUserIdStatement.executeQuery();

                if (userIdResultSet.next()) {
                    String lecturerUserId = userIdResultSet.getString("userID");

                    // Insert review details into 'form'
                    String insertFormQuery = "INSERT INTO form (formName, lec) VALUES (?, ?)";
                    PreparedStatement insertFormStmt = dbConnect.prepareStatement(insertFormQuery, Statement.RETURN_GENERATED_KEYS);
                    insertFormStmt.setString(1, reviewTitleText);
                    insertFormStmt.setString(2, lecturerUserId);

                    int rowsInserted = insertFormStmt.executeUpdate();
                    if (rowsInserted > 0) {
                        ResultSet generatedKeys = insertFormStmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int formId = generatedKeys.getInt(1);

                            // Call saveQuestions with lecturerUserId
                            saveQuestions(formId, lecturerUserId);
                        }
                    } else {
                        registrationMessageFailureLabel.setText("Failed to create the review. Please try again.");
                    }
                } else {
                    registrationMessageFailureLabel.setText("Lecturer not found. Please select a valid lecturer.");
                }
            } else {
                registrationMessageFailureLabel.setText("Failed to connect to the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrationMessageFailureLabel.setText("An error occurred while creating the review.");
        }
    }


    private void saveQuestions(int formId, String lecturerUserId) {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();

            if (dbConnect != null) {
                for (String question : questions) {
                    String[] parts = question.split(": ", 2);
                    String category = parts[0];
                    String questionText = parts[1];
                    String unit = CRUnit.getValue();

                    String insertQuestionQuery = "INSERT INTO formcontents (question, category, formID, unit, lec) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertQuestionStmt = dbConnect.prepareStatement(insertQuestionQuery);
                    insertQuestionStmt.setString(1, questionText);
                    insertQuestionStmt.setInt(2, getCategoryId(category));
                    insertQuestionStmt.setInt(3, formId);
                    insertQuestionStmt.setString(4, unit);
                    insertQuestionStmt.setString(5, lecturerUserId); // Use lecturerUserId correctly

                    insertQuestionStmt.executeUpdate();
                }
                registrationSuccessMessageLabel.setText("Successfully created a review!");
            } else {
                registrationMessageFailureLabel.setText("Failed to connect to the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrationMessageFailureLabel.setText("An error occurred while saving the questions.");
        }
    }

    private int getCategoryId(String category) {
        switch (category) {
            case "Knowledge and Expertise":
                return 1;
            case "Teaching Style and Delivery":
                return 2;
            case "Clarity of Explanations":
                return 3;
            case "Engagement and Interaction with Students":
                return 4;
            case "Responsiveness and Availability":
                return 5;
            case "Course Organization and Structure":
                return 6;
            case "Fairness and Objectivity in Grading":
                return 7;
            case "Use of Visual Aids and Technology":
                return 8;
            case "Providing Relevant and Up-to-Date Content":
                return 9;
            case "Encouraging Critical Thinking and Discussion":
                return 10;
            case "Providing Constructive Feedback":
                return 11;
            case "Punctuality and Time Management":
                return 12;
            case "Respect for Diverse Perspectives":
                return 13;
            case "Enthusiasm and Passion for Teaching":
                return 14;
            case "Overall Effectiveness in Facilitating Learning":
                return 15;
            default:
                return -1; // Invalid category
        }
    }

    private void fetchLecturers() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();

            if (dbConnect != null) {
                String query = "SELECT firstName, lastName FROM users WHERE userType = 'Lecturer' AND userID LIKE 'L%'";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String fullName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
                    lecturerList.add(fullName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchUnits() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();

            if (dbConnect != null) {
                String query =  "SELECT unitID, unitName FROM units";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String unitName = resultSet.getString("unitName");
                    int unitID = resultSet.getInt("unitID");
                    unitList.add(unitName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        submitCreatedReviewButton.setDisable(true);
        saveFormDetails();
        submitCreatedReviewButton.setDisable(false);
    }
}
