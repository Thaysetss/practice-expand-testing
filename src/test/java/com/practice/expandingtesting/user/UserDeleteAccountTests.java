package com.practice.expandingtesting.user;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.USER_DELETE_ACCOUNT_SUCCESS;
import static com.practice.expandingtesting.data.MessagesData.USER_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class UserDeleteAccountTests {
    @Test
    @DisplayName("Test the account was deleted successfully when the token is valid.")
    void deleteDeleteAccountSuccess() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        ValidatableResponse response = new UsersClient().deleteAccount(user);
        response.statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is(USER_DELETE_ACCOUNT_SUCCESS.message));
    }

    @Test
    @DisplayName("Test if the account was deleted when the request has an invalid token.")
    void deleteDeleteAccountInvalidToken() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setToken("123454");
        ValidatableResponse response = new UsersClient().deleteAccount(user);
        response.statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED))
                .body("message", is(USER_UNAUTHORIZED.message));
    }
}
