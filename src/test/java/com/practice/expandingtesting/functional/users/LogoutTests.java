package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.UsersMessages.LOGOUT_SUCCESS;
import static com.practice.expandingtesting.data.UsersMessages.UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class LogoutTests {
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
    @DisplayName("Test the logout successfully when the token is valid.")
    void deleteLogoutUserSuccess() {
        new UsersClient().deleteLogout(this.user)
                .statusCode(SC_OK)
                .body("success", is(true),
                        "status", is(SC_OK),
                        "message", is(LOGOUT_SUCCESS.message));
    }

    @Test
    @DisplayName("Test the logout with invalid token.")
    void deleteLogoutUserInvalidToken() {
        this.user.setToken("123454");
        new UsersClient().deleteLogout(this.user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false),
                        "status", is(SC_UNAUTHORIZED),
                        "message", is(UNAUTHORIZED.message));
    }
}