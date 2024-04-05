package com.example.nlrs_main;

import com.example.nlrs_main.DatabaseConnector.ReadWriteDB;
import com.example.nlrs_main.NLS.NaturalLanguageAnalyzer;
import com.example.nlrs_main.Users;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;

public class AnswerReview_Controller extends Users {
    @FXML
    private VBox questionReviewContainer;
    @FXML
    private Label message;

    private List<QuestionReviewContainer> questionReviewContainers;
    public String selectedUnitName;

    private String studentID;

    public void initialize(String unitName) {
        this.selectedUnitName = unitName;
        // Remove the code related to setting studentID
        questionReviewContainers = new ArrayList<>();
        setQuestionsFromDB();
    }


    @FXML
    private void submitReviews() {
        for (QuestionReviewContainer container : questionReviewContainers) {
            String review = container.getReview();
            System.out.println("Review submitted for question: " + container.getQuestion() + "\nReview: " + review);
            int reviewScore = analyzeSentiment(review);
            System.out.println("This is the rating for the review of the question: " + reviewScore);
            storeFeedback(container.getQuestion(), review, reviewScore, studentID);
        }
    }
    private void setQuestionsFromDB() {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT question, category FROM formcontents WHERE unit = ?";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, selectedUnitName);

                ResultSet resultSet = statement.executeQuery();
                int questionNumber = 1;
                while (resultSet.next()) {
                    String question = resultSet.getString("question");
                    int categoryID = resultSet.getInt("category");
                    QuestionReviewContainer container = new QuestionReviewContainer(questionNumber, question, categoryID);
                    questionReviewContainers.add(container);
                    questionReviewContainer.getChildren().add(container.getRoot());
                    questionNumber++;
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

    private void storeFeedback(String question, String comment, int score, String studentID) {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String lecturerID = getLecturerIDForUnit(selectedUnitName);

                String query = "INSERT INTO feedback (userID, comment, CaID, stdID, CoScore, unit) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, lecturerID);
                statement.setString(2, comment);
                statement.setInt(3, getCategoryID(question));
                statement.setString(4, studentID);
                statement.setInt(5, score);
                statement.setString(6, selectedUnitName);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Feedback stored successfully");
                    message.setText("Feedback Submitted successfully");
                } else {
                    System.out.println("Failed to store feedback");
                    message.setText("Failed to store feedback");
                }
                statement.close();
            } else {
                System.out.println("Database Connection Failed");
                message.setText("Database Connection Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
            message.setText("Error occurred: " + e.getMessage());

        }
    }

    private int analyzeSentiment(String text) {
        NaturalLanguageAnalyzer nls = new NaturalLanguageAnalyzer();
        return nls.analyzeSentiment(text);
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }


    private int getCategoryID(String question) {
        for (QuestionReviewContainer container : questionReviewContainers) {
            if (container.getQuestion().equals(question)) {
                return container.getCategoryID();
            }
        }
        return 0;
    }

    private String getLecturerIDForUnit(String unitName) {
        try {
            ReadWriteDB con = new ReadWriteDB();
            Connection dbConnect = con.getConnection();
            if (dbConnect != null) {
                String query = "SELECT userID FROM units WHERE unitName = ?";
                PreparedStatement statement = dbConnect.prepareStatement(query);
                statement.setString(1, unitName);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String lecturerID = resultSet.getString("userID");
                    resultSet.close();
                    statement.close();
                    dbConnect.close();
                    return lecturerID;
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
        return "";
    }

    private static class QuestionReviewContainer {
        private Label questionLabel;
        private TextArea reviewTextArea;
        private VBox root;
        private int categoryID;

        public QuestionReviewContainer(int questionNumber, String question, int categoryID) {
            this.categoryID = categoryID;
            questionLabel = new Label("Question " + questionNumber + ": " + question);
            reviewTextArea = new TextArea();
            reviewTextArea.setPromptText("Enter your review here");

            root = new VBox(10, questionLabel, reviewTextArea);
        }

        public String getQuestion() {
            return questionLabel.getText().substring(questionLabel.getText().indexOf(":") + 2); // Extract question text
        }

        public String getReview() {
            return reviewTextArea.getText();
        }

        public VBox getRoot() {
            return root;
        }

        public int getCategoryID() {
            return categoryID;
        }



    }
}