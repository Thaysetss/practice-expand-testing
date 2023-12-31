package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.UsersMessages.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class ForgotPasswordTests {
    UserModel user = null;

    @BeforeEach
    void setUp() {
        this.user = new UserUtils().authenticationNewUser();
    }

    @AfterEach
    void tearDown() {
        new UsersClient().deleteAccount(this.user);
    }

    @Test
    @DisplayName("Test que request for forgot password with valid email.")
    void forgotPasswordSuccess() {
        new UsersClient().postForgotPassword(this.user)
                .statusCode(SC_OK)
                .body("success", is(true),
                        "status", is(SC_OK),
                        "message", is("Password reset link successfully sent to "
                                + this.user.getEmail() + ". Please verify by clicking on the given link"));
    }

    @Test
    @DisplayName("Test the request with an invalid email.")
    void forgotPasswordInvalidEmail() {
        this.user.setEmail("testemail");
        new UsersClient().postForgotPassword(this.user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false),
                        "status", is(SC_BAD_REQUEST),
                        "message", is(FORGOT_PASSWORD_INVALID_EMAIL.message));
    }

    @Test
    @DisplayName("Test the request with a nonexistent email.")
    void forgotPasswordNonexistentEmail() {
        this.user.setEmail("this_test_non_existent_email@nonexistent.com");
        new UsersClient().postForgotPassword(this.user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false),
                        "status", is(SC_UNAUTHORIZED),
                        "message", is(FORGOT_PASSWORD_NONEXISTENT_EMAIL.message));
    }
}