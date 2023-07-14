package com.practice.expandingtesting.client.users;

import com.google.gson.Gson;
import com.practice.expandingtesting.config.ConfigurationManager;
import com.practice.expandingtesting.model.UserModel;
import io.restassured.response.ValidatableResponse;

import static com.practice.expandingtesting.data.EndpointsData.*;
import static io.restassured.RestAssured.given;

public class UsersClient {

    private final String urlUserRegister = ConfigurationManager.getConfiguration().basePath() + USERS_REGISTER.endpoint;
    private final String urlUserLogin = ConfigurationManager.getConfiguration().basePath() + USERS_LOGIN.endpoint;
    private final String urlGetProfile = ConfigurationManager.getConfiguration().basePath() + USERS_PROFILE.endpoint;
    private final String urlLogout = ConfigurationManager.getConfiguration().basePath() + USERS_DELETE_LOGOUT.endpoint;

    private final String urlDeleteAccount = ConfigurationManager.getConfiguration().basePath() + USERS_DELETE_ACCOUNT.endpoint;


    private String generateBody(UserModel user) {
        return new Gson().toJson(user);
    }

    public ValidatableResponse postRegisterNewRandomUser(UserModel user) {
        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(generateBody(user))
                .when()
                .post(urlUserRegister)
                .then();
    }

    public ValidatableResponse postLogin(UserModel user) {
        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(generateBody(user))
                .when()
                .post(urlUserLogin)
                .then();
    }

    public UserModel postLoginReturnUser(UserModel user) {
        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(generateBody(user))
                .when()
                .post(urlUserLogin)
                .then()
                .extract()
                .jsonPath()
                .getObject("data", UserModel.class);
    }

    public ValidatableResponse getProfile(UserModel user) {
        return given()
                .header("x-auth-token", user.getToken())
                .when()
                .get(urlGetProfile)
                .then();
    }

    public ValidatableResponse patchProfile(UserModel user) {
        return given()
                .header("x-auth-token", user.getToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(generateBody(user))
                .when()
                .patch(urlGetProfile)
                .then();
    }

    public ValidatableResponse deleteLogout(UserModel user) {
        return given()
                .header("x-auth-token", user.getToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .delete(urlLogout)
                .then();
    }

    public ValidatableResponse deleteAccount(UserModel user) {
        return given()
                .header("x-auth-token", user.getToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .delete(urlDeleteAccount)
                .then();
    }
}