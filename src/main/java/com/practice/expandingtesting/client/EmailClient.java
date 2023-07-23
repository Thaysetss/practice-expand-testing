package com.practice.expandingtesting.client;

import com.practice.expandingtesting.config.ConfigurationManager;
import com.practice.expandingtesting.model.EmailModel;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class EmailClient {

    private static final String mailsacURL = ConfigurationManager.getConfiguration().mailsacPath() + "/addresses/";

    public List<EmailModel> getMessageFromEmail(String email) {
        String finalUrl = mailsacURL + email + "/messages";
        return List.of(given()
                .header("accept", "application/json")
                .header("Mailsac-Key", ConfigurationManager.getConfiguration().mailsacKey())
                .param("limit", 1)
                .param("_mailsacKey", ConfigurationManager.getConfiguration().mailsacKey())
                .when()
                .get(finalUrl)
                .then()
                .extract()
                .as(EmailModel[].class));
    }

    public ValidatableResponse deleteEmailMessage(String email, String _id) {
        String finalUrl = mailsacURL + email + "/messages/";
        return given()
                .header("accept", "application/json")
                .header("Mailsac-Key", "k_NcdSC4AqRc4d57xTx1uzGgNvJWOIAYYQDS3V")
                .delete(finalUrl + _id)
                .then();
    }
}
