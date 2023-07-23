package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.EmailFactory;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;

public class ResetPasswordTests {

    String email = "test15@mailsac.com";
    UserModel user = null;

    @BeforeEach
    void setUp() {
        this.user = new UserUtils().authenticationNewUserWithEmail(this.email);
    }

    @AfterEach
    void tearDown() {
        new UsersClient().deleteAccount(this.user);
    }

    @Test
    @DisplayName("Test que request for forgot password with valid email.")
    void forgotPasswordSuccess() throws InterruptedException {
        new UsersClient().postForgotPassword(this.user);
        String token = new EmailFactory().returnTokenFromEmail(this.email);
        System.out.println("TOKEN: " + token);
        String password = new Faker().internet().password();
        new UsersClient().postResetPassword(token, password)
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is(USERS_RESET_PASSWORD_SUCCESS.message));
    }

    @Test
    @DisplayName("Test que request for forgot password with an invalid token with less characters")
    void forgotPasswordLessCharactersToken() throws InterruptedException {
        new UsersClient().postForgotPassword(this.user);
        //Getting the token from email to delete the message and clean the inbox
        new EmailFactory().returnTokenFromEmail(this.email);
        String token = "NewInvalidTOken";
        String password = new Faker().internet().password();
        new UsersClient().postResetPassword(token, password)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("status", is(SC_BAD_REQUEST))
                .body("message", is(USERS_CHANGE_PASSWORD_MINIMUM_TOKEN.message));
    }

    @Test
    @DisplayName("Test que request for forgot password with an invalid token with 64 characters.")
    void forgotPasswordInvalidToken() throws InterruptedException {
        new UsersClient().postForgotPassword(this.user);
        //Getting the token from email to delete the message and clean the inbox
        new EmailFactory().returnTokenFromEmail(this.email);
        String token = "cb89a9b48c6348d094652afd55b1fb8448f2bc14801c40da9d53ea6f055a3800";
        String password = new Faker().internet().password();
        new UsersClient().postResetPassword(token, password)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED))
                .body("message", is(USERS_CHANGE_PASSWORD_INVALID_TOKEN.message));
    }
}