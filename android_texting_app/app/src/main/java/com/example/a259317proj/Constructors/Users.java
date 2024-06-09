package com.example.a259317proj.Constructors;

public class Users {
    String username, email, password, userID, lastmess;


    public Users(){

    }

    public Users(String username, String email, String password,  String userID, String lastmess) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.lastmess = lastmess;
    }

    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLastmess() {
        return lastmess;
    }

    public void setLastmess(String lastmess) {
        this.lastmess = lastmess;
    }
}
