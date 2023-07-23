package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.*;
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
        String newPassword = "TestNewPassword010203!";
        new UsersClient().postChangePassword(this.user, newPassword)
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is(USERS_CHANGE_PASSWORD_SUCCESS.message));
    }

    @Test
    @DisplayName("Test que Change Password endpoint using an invalid token.")
    void changePasswordTokenInvalid(){
        String newPassword = "TestNewPassword010203!";
        this.user.setToken("TESTEInvalidToken");
        new UsersClient().postChangePassword(this.user, newPassword)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED))
                .body("message", is(USERS_UNAUTHORIZED.message));
    }
    @Test
    @DisplayName("Test que Change Password endpoint using an invalid password as new password.")
    void changePasswordNewPasswordInvalid(){
        String newPassword = "123";
        new UsersClient().postChangePassword(this.user, newPassword)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("status", is(SC_BAD_REQUEST))
                .body("message", is(USERS_NEW_PASSWORD_CHARACTERS.message));
    }
}
