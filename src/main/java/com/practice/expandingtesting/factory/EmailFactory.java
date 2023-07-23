package com.practice.expandingtesting.factory;

import com.practice.expandingtesting.client.EmailClient;
import com.practice.expandingtesting.model.EmailModel;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;

public class EmailFactory {

    public String returnTokenFromEmail(String email) throws InterruptedException {

        String token = null;
        List<EmailModel> emailModel;
        do {
            emailModel = new EmailClient().getMessageFromEmail(email);
            //Wait one second to check the email again, to avoid mailsac service block the connection.
            Thread.sleep(1000);
        } while (emailModel.isEmpty());

        for (String link : emailModel.get(0).getLinks()) {
            if (link.contains("reset-password/")) {
                token = link.replace("https://practice.expandtesting.com/notes/app/reset-password/", "");
                new EmailClient().deleteEmailMessage(email, emailModel.get(0).get_id())
                        .statusCode(SC_OK);
            }
        }
        return token;
    }
}