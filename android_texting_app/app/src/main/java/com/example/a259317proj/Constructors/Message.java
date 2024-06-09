package com.example.a259317proj.Constructors;

public class Message {

    String userID, message, messageID;
    Long time;

    public Message(){

    }

    public Message(String userID, String message, String messageID, Long time) {
        this.userID = userID;
        this.message = message;
        this.time = time;
    }

    public Message(String userID, String message) {
        this.userID = userID;
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
