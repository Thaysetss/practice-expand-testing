package com.practice.expandingtesting.data;

public enum UsersMessages {
    REGISTER_ACCOUNT_CREATED("User account created successfully"),
    REGISTER_INVALID_EMAIL("A valid email address is required"),
    REGISTER_NULL_NAME("User name must be between 4 and 30 characters"),
    REGISTER_NULL_PASSWORD("Password must be between 6 and 30 characters"),
    LOGIN_SUCCESS("Login successful"),
    LOGIN_INCORRECT_EMAIL_PASSWORD("Incorrect email address or password"),
    LOGIN_PASSWORD_LESS_CHARACTERS("Password must be between 6 and 30 characters"),
    PROFILE_SUCCESS("Profile successful"),
    UNAUTHORIZED("Access token is not valid or has expired, you will need to login"),
    LOGOUT_SUCCESS("User has been successfully logged out"),
    DELETE_ACCOUNT_SUCCESS("Account successfully deleted"),
    USERS_FORGOT_PASSWORD_SUCCESS("Password reset link successfully sent to {S}. Please verify by clicking on the given link"),
    FORGOT_PASSWORD_INVALID_EMAIL("A valid email address is required"),
    FORGOT_PASSWORD_NONEXISTENT_EMAIL("No account found with the given email address"),
    VERIFY_TOKEN_SUCCESS("The provided password reset token is valid"),
    VERIFY_TOKEN_INVALID("The provided password reset token is invalid or has expired"),
    RESET_PASSWORD_SUCCESS("The password was successfully updated"),
    CHANGE_PASSWORD_SUCCESS("The password was successfully updated"),
    CHANGE_PASSWORD_MINIMUM_TOKEN("Token must be between 64 characters"),
    CHANGE_PASSWORD_INVALID_TOKEN("The password reset token is invalid or has expired"),
    NEW_PASSWORD_CHARACTERS("New password must be between 6 and 30 characters");

    public final String message;

    UsersMessages(String message) {
        this.message = message;
    }
}