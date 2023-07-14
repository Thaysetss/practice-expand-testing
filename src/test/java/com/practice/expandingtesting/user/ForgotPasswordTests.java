package com.practice.expandingtesting.user;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class ForgotPasswordTests {

    @Test
    @DisplayName("Test que request for forgot password with valid email.")
    void forgotPasswordSucces() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        new UsersClient().postForgotPassword(user)
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is("Password reset link successfully sent to "
                        + user.getEmail() + ". Please verify by clicking on the given link"));
    }

    @Test
    @DisplayName("Test the request with an invalid email.")
    void forgotPasswordInvalidEmail() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setEmail("testemail");
        new UsersClient().postForgotPassword(user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("status", is(SC_BAD_REQUEST))
                .body("message", is(USERS_FORGOT_PASSWORD_INVALID_EMAIL.message));
    }

    @Test
    @DisplayName("Test the request with a nonexistent email.")
    void forgotPasswordNonexistentEmail() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setEmail("this_test_non_existent_email@nonexistent.com");
        new UsersClient().postForgotPassword(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED))
                .body("message", is(USERS_FORGOT_PASSWORD_NONEXISTENT_EMAIL.message));
    }
}
