package com.example.nlrs_main;

import com.example.nlrs_main.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Login_Page extends Users
{
    StringProperty userID;
    StringProperty password;

    Login_Page()
    {
        userID = new SimpleStringProperty();
        password = new SimpleStringProperty();
    }

    public String getuserID() {
        return userID.get();
    }

    public StringProperty userIDProperty() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID.set(userID);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

}
