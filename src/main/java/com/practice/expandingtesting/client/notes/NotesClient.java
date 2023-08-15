package com.practice.expandingtesting.client.notes;

import com.practice.expandingtesting.config.ConfigurationManager;
import com.practice.expandingtesting.model.NotesModel;
import com.practice.expandingtesting.model.UserModel;
import io.restassured.response.ValidatableResponse;

import static com.practice.expandingtesting.data.EndpointsData.NOTES;
import static io.restassured.RestAssured.given;

public class NotesClient {

    private final static String basePath = ConfigurationManager.getConfiguration().basePath();
    private final String urlNotes = basePath + NOTES.endpoint;

    public ValidatableResponse postNotes(UserModel user, NotesModel notes) {
        return given()
                .header("x-auth-token", user.getToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("title", notes.getTitle())
                .formParam("description", notes.getDescription())
                .formParam("category", notes.getCategory())
                .when()
                .post(urlNotes)
                .then();
    }

    public ValidatableResponse getAllNotes(UserModel user) {
        return given()
                .header("x-auth-token", user.getToken())
                .header("accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .when()
                .get(urlNotes)
                .then();
    }
}
