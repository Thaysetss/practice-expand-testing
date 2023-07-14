package com.practice.expandingtesting.data;

public enum MessagesData {

    USER_REGISTER_ACCOUNT_CREATED("User account created successfully"),
    USER_REGISTER_INVALID_EMAIL("A valid email address is required"),
    USER_REGISTER_NULL_NAME("User name must be between 4 and 30 characters"),
    USER_REGISTER_NULL_PASSWORD("Password must be between 6 and 30 characters"),
    USER_LOGIN_SUCCESS("Login successful"),
    USER_LOGIN_INCORRECT_EMAIL_PASSWORD("Incorrect email address or password"),
    USER_LOGIN_PASSWORD_LESS_CHARACTERS("Password must be between 6 and 30 characters"),
    USER_PROFILE_SUCCESS("Profile successful"),
    USER_UNAUTHORIZED("Access token is not valid or has expired, you will need to login"),

    USER_LOGOUT_SUCCESS("User has been successfully logged out"),
    USER_DELETE_ACCOUNT_SUCCESS("Account successfully deleted");

    public final String message;

    MessagesData(String message) {
        this.message = message;
    }
}