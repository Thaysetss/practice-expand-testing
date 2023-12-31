package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.UsersMessages.DELETE_ACCOUNT_SUCCESS;
import static com.practice.expandingtesting.data.UsersMessages.UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class DeleteAccountTests {
    @Test
    @DisplayName("Test the account was deleted successfully when the token is valid.")
    void deleteDeleteAccountSuccess() {
        UserModel user = new UserUtils().authenticationNewUser();
        new UsersClient().deleteAccount(user)
                .statusCode(SC_OK)
                .body("success", is(true),
                        "status", is(SC_OK),
                        "message", is(DELETE_ACCOUNT_SUCCESS.message));
    }

    @Test
    @DisplayName("Test if the account was deleted when the request has an invalid token.")
    void deleteDeleteAccountInvalidToken() {
        UserModel user = new UserUtils().authenticationNewUser();
        user.setToken("123454");
        new UsersClient().deleteAccount(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false),
                        "status", is(SC_UNAUTHORIZED),
                        "message", is(UNAUTHORIZED.message));
    }

    @Test
    @DisplayName("Test if the account exists after deleting it.")
    void validateAccountAfterDelete() {
        UserModel user = new UserUtils().authenticationNewUser();
        new UsersClient().deleteAccount(user);
        new UsersClient().getProfile(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false),
                        "status", is(SC_UNAUTHORIZED),
                        "message", is(UNAUTHORIZED.message));
    }
}
