package com.practice.expandingtesting.client.users;

import com.google.gson.Gson;
import com.practice.expandingtesting.config.ConfigurationManager;
import com.practice.expandingtesting.model.UserModel;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static com.practice.expandingtesting.data.EndpointsData.*;
import static io.restassured.RestAssured.given;

public class UsersClient {

    private final String basePath = ConfigurationManager.getConfiguration().basePath();
    private final String urlUserRegister = basePath + USERS_REGISTER.endpoint;
    private final String urlUserLogin = basePath + USERS_LOGIN.endpoint;
    private final String urlGetProfile = basePath + USERS_PROFILE.endpoint;
    private final String urlLogout = basePath + USERS_DELETE_LOGOUT.endpoint;
    private final String urlForgot = basePath + USERS_FORGOT_PASSWORD.endpoint;
    private final String urlReset = basePath + USERS_RESET_PASSWORD.endpoint;
    private final String urlDeleteAccount = basePath + USERS_DELETE_ACCOUNT.endpoint;
    private final String urlChangePassword = basePath + USERS_CHANGE_PASSWORD.endpoint;
    private final String urlVerifyToken = basePath + USERS_VERIFY_RESET_PASSWORD_TOKEN.endpoint;


    private String generateBody(UserModel user) {
        return new Gson().toJson(user);
    }

    public ValidatableResponse postRegisterNewUser(UserModel user) {
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
        return given().
                header("x-auth-token", user.getToken())
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

    public ValidatableResponse postForgotPassword(UserModel user) {
        return given()
                .header("x-auth-token", user.getToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(generateBody(user))
                .when()
                .post(urlForgot)
                .then();
    }

    public ValidatableResponse postChangePassword(UserModel user, String newPassword) {
        return given()
                .header("x-auth-token", user.getToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("currentPassword", user.getPassword())
                .formParam("newPassword", newPassword)
                .when()
                .post(urlChangePassword)
                .then();
    }

    public ValidatableResponse postResetPassword(String token, String newPassword) {
        return given()
                .config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                        .encodeContentTypeAs("x-www-form-urlencoded", ContentType.URLENC)))
                .header("accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("token", token)
                .formParam("newPassword", newPassword)
                .when()
                .post(urlReset)
                .then();
    }

    public ValidatableResponse postVerifyResetPasswordToken(String token){
        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("token", token)
                .when()
                .post(urlVerifyToken)
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