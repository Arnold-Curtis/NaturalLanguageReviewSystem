package com.example.nlrs_main.DatabaseConnector;

import com.example.nlrs_main.Users;

import java.sql.*;

public class ReadWriteDB extends Users {
    private Connection databaseLink;

    public Connection getConnection() {
        String dbName = "nlrs";
        String user = "root";
        String password = "alvinmajawa2020*";
        String url = "jdbc:mysql://localhost/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

    public boolean getLoginDetailsFromDB(String userID, String password) {
        try {
            Connection dbConnect = getConnection();

            String sql = "SELECT COUNT(*) FROM users WHERE userID = ? AND password = ?";
            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(sql)) {
                preparedStatement.setString(1, userID);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserDetailsFromDB(String userID) {
        try {
            Connection dbConnect = getConnection();

            String sql = "SELECT firstName, lastName FROM users WHERE userID = ?";
            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(sql)) {
                preparedStatement.setString(1, userID);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    System.out.println(firstName + " " + lastName);
                    return firstName + " " + lastName;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
