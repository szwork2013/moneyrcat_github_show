package com.emperises.monercat.adapter;

public class MessageModel {
    private String message = "";
    private String date = "";
    private boolean isA = true;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isA() {
        return isA;
    }

    public void setA(boolean isA) {
        this.isA = isA;
    }
}
