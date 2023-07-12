package com.practice.expandingtesting.client.users;

import com.google.gson.Gson;
import com.practice.expandingtesting.config.ConfigurationManager;
import com.practice.expandingtesting.model.UserModel;
import io.restassured.response.Response;

import static com.practice.expandingtesting.data.EndpointsData.USERS_REGISTER;
import static io.restassured.RestAssured.given;

public class UsersClient {

    private final String urlUserRegister = ConfigurationManager.getConfiguration().basePath() + USERS_REGISTER.endpoint;

    private String generateBody(UserModel user) {
        return new Gson().toJson(user);
    }

    public Response postNewRandomUser(UserModel user) {
        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(generateBody(user))
                .when()
                .post(urlUserRegister)
                .then()
                .extract()
                .response();
    }
}
