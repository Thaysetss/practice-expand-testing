package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.UsersMessages.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;

public class ChangePasswordTests {

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
    @DisplayName("Test que Change Password endpoint using valid data to get success in the process.")
    void changePasswordSuccess() {
        String newPassword = new Faker().internet().password();
        new UsersClient().postChangePassword(this.user, newPassword)
                .statusCode(SC_OK)
                .body("success", is(true),
                        "status", is(SC_OK),
                        "message", is(CHANGE_PASSWORD_SUCCESS.message));
    }

    @Test
    @DisplayName("Test que Change Password endpoint using an invalid token.")
    void changePasswordTokenInvalid() {
        String newPassword = new Faker().internet().password();
        this.user.setToken("TESTEInvalidToken");
        new UsersClient().postChangePassword(this.user, newPassword)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false),
                        "status", is(SC_UNAUTHORIZED),
                        "message", is(UNAUTHORIZED.message));
    }

    @Test
    @DisplayName("Test que Change Password endpoint using an invalid password as new password.")
    void changePasswordNewPasswordInvalid() {
        String newPassword = "123";
        new UsersClient().postChangePassword(this.user, newPassword)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false),
                        "status", is(SC_BAD_REQUEST),
                        "message", is(NEW_PASSWORD_CHARACTERS.message));
    }
}
