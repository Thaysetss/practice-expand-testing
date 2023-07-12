package com.practice.expandingtesting.data;

public enum MessagesData {

    USER_REGISTER_ACCOUNT_CREATED("User account created successfully"),
    USER_REGISTER_INVALID_EMAIL("A valid email address is required"),
    USER_REGISTER_NULL_NAME("User name must be between 4 and 30 characters"),
    USER_REGISTER_NULL_PASSWORD("Password must be between 6 and 30 characters");

    public final String message;

    MessagesData(String message) {
        this.message = message;
    }
}