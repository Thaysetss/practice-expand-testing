package com.practice.expandingtesting.data;

public enum EndpointsData {

    USERS_REGISTER("/users/register"),
    USERS_LOGIN("/users/login"),
    USERS_PROFILE("/users/profile"),
    USERS_FORGOT_PASSWORD("/users/forgot-password"),
    USERS_DELETE_LOGOUT("/users/logout"),
    NOTES("/notes");
    public final String endpoint;

    EndpointsData(String endpoint) {
        this.endpoint = endpoint;
    }
}