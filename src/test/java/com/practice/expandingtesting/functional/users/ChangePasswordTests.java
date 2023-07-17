package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class ChangePasswordTests {
    @Test
    @DisplayName("Test que request for forgot password with valid email.")
    void forgotPasswordSucces() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setCurrentPassword("NEWPASSWORD010203");
        new UsersClient().postForgotPassword(user)
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is("Password reset link successfully sent to "
                        + user.getEmail() + ". Please verify by clicking on the given link"));
    }
}
