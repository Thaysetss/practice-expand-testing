package com.practice.expandingtesting.user;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.USER_LOGOUT_SUCCESS;
import static com.practice.expandingtesting.data.MessagesData.USER_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class UsersLogoutTests {

    @Test
    @DisplayName("Test the logout successfully when the token is valid.")
    void deleteLogoutUserSuccess() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        new UsersClient().deleteLogout(user)
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is(USER_LOGOUT_SUCCESS.message));
    }

    @Test
    @DisplayName("Test the logout with invalid token.")
    void deleteLogoutUserInvalidToken() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setToken("123454");
        new UsersClient().deleteLogout(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED))
                .body("message", is(USER_UNAUTHORIZED.message));
    }
}
