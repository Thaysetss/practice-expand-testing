package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.EmailFactory;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;

public class VerifyResetTokenTests {

    String email = "test15@mailsac.com";
    UserModel user;

    @BeforeEach
    void setUp() {
        this.user = new UserUtils().authenticationNewUserWithEmail(this.email);
    }

    @AfterEach
    void tearDown() {
        new UsersClient().deleteAccount(this.user);
    }

    @Test
    @DisplayName("Test que request Verify Reset Password Token with valid token.")
    void verifyTokenSuccess() throws InterruptedException {
        new UsersClient().postForgotPassword(this.user);
        String token = new EmailFactory().returnTokenFromEmail(this.email);
        new UsersClient().postVerifyResetPasswordToken(token)
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is(USERS_VERIFY_TOKEN_SUCCESS.message));
    }

    @Test
    @DisplayName("Test que request Verify Reset Password Token with invalid token.")
    void verifyTokenInvalidToken() throws InterruptedException {
        new UsersClient().postForgotPassword(this.user);
        new EmailFactory().returnTokenFromEmail(this.email);
        String token = "NewInvalidTOken";
        new UsersClient().postVerifyResetPasswordToken(token)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED))
                .body("message", is(USERS_VERIFY_TOKEN_INVALID.message));
    }

    @Test
    @DisplayName("Test que request Verify Reset Password Token with invalid token with correct token size.")
    void verifyTokenInvalidCorrectSizeToken() throws InterruptedException {
        new UsersClient().postForgotPassword(this.user);
        //Getting the token from email to delete the message and clean the inbox
        new EmailFactory().returnTokenFromEmail(this.email);
        String token = "cb89a9b48c6348d094652afd55b1fb8448f2bc14801c40da9d53ea6f055a3800";
        new UsersClient().postVerifyResetPasswordToken(token)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED))
                .body("message", is(USERS_VERIFY_TOKEN_INVALID.message));
    }
}