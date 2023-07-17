package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.USERS_DELETE_ACCOUNT_SUCCESS;
import static com.practice.expandingtesting.data.MessagesData.USERS_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class DeleteAccountTests {
    @Test
    @DisplayName("Test the account was deleted successfully when the token is valid.")
    void deleteDeleteAccountSuccess() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        new UsersClient().deleteAccount(user)
                .statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is(USERS_DELETE_ACCOUNT_SUCCESS.message));
    }

    @Test
    @DisplayName("Test if the account was deleted when the request has an invalid token.")
    void deleteDeleteAccountInvalidToken() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setToken("123454");
        new UsersClient().deleteAccount(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED))
                .body("message", is(USERS_UNAUTHORIZED.message));
    }

    @Test
    @DisplayName("Test if the account exists after deleting it.")
    void validateAccountAfterDelete() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        new UsersClient().deleteAccount(user);
        new UsersClient().getProfile(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED))
                .body("message", is(USERS_UNAUTHORIZED.message));
    }
}
