package com.practice.expandingtesting.data;

public enum MessagesData {
    USERS_REGISTER_ACCOUNT_CREATED("User account created successfully"),
    USER_REGISTER_INVALID_EMAIL("A valid email address is required"),
    USERS_REGISTER_NULL_NAME("User name must be between 4 and 30 characters"),
    USERS_REGISTER_NULL_PASSWORD("Password must be between 6 and 30 characters"),
    USERS_LOGIN_SUCCESS("Login successful"),
    USERS_LOGIN_INCORRECT_EMAIL_PASSWORD("Incorrect email address or password"),
    USERS_LOGIN_PASSWORD_LESS_CHARACTERS("Password must be between 6 and 30 characters"),
    USERS_PROFILE_SUCCESS("Profile successful"),
    USERS_UNAUTHORIZED("Access token is not valid or has expired, you will need to login"),
    USERS_LOGOUT_SUCCESS("User has been successfully logged out"),
    USERS_DELETE_ACCOUNT_SUCCESS("Account successfully deleted"),
    USERS_FORGOT_PASSWORD_SUCCESS("Password reset link successfully sent to {S}. Please verify by clicking on the given link"),
    USERS_FORGOT_PASSWORD_INVALID_EMAIL("A valid email address is required"),
    USERS_FORGOT_PASSWORD_NONEXISTENT_EMAIL("No account found with the given email address"),
    USERS_RESET_PASSWORD_SUCCESS("The password was successfully updated"),
    USERS_CHANGE_PASSWORD_SUCCESS("The password was successfully updated"),
    USERS_CHANGE_PASSWORD_MINIMUM_TOKEN("Token must be between 64 characters"),
    USERS_CHANGE_PASSWORD_INVALID_TOKEN("The password reset token is invalid or has expired"),
    USERS_NEW_PASSWORD_CHARACTERS("New password must be between 6 and 30 characters");
    public final String message;

    MessagesData(String message) {
        this.message = message;
    }
}