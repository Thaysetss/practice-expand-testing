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

import static com.practice.expandingtesting.data.MessagesData.USERS_RESET_PASSWORD_SUCCESS;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

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
        assertThat(token, not("74ab3235560c456bb34961c2ac667e503550af2ec31c4d8d960544970f96447b"));
        String password = new Faker().internet().password();
        System.out.println("NEW PASSWORD: " + password);

        new UsersClient().postResetPassword(token, password)
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is(USERS_RESET_PASSWORD_SUCCESS.message));
    }
}