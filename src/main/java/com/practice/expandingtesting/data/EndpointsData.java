package com.practice.expandingtesting.data;

public enum EndpointsData {

    USERS_REGISTER("/users/register"),
    USERS_LOGIN("/users/login"),
    USERS_PROFILE("/users/profile"),
    USERS_FORGOT_PASSWORD("/users/forgot-password"),
    USERS_DELETE_LOGOUT("/users/logout"),
    USERS_DELETE_ACCOUNT("/users/delete-account"),
    NOTES("/notes");
    public final String endpoint;

    EndpointsData(String endpoint) {
        this.endpoint = endpoint;
    }
}