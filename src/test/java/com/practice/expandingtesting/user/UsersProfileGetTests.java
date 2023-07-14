package com.practice.expandingtesting.user;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.USER_PROFILE_SUCCESS;
import static com.practice.expandingtesting.data.MessagesData.USER_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class UsersProfileGetTests {

    @Test
    @DisplayName("Validate the Get method in profile endpoint when the user is valid.")
    void getProfileValidLogin() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        ValidatableResponse response = new UsersClient().getProfile(user.getToken());
        response.statusCode(SC_OK)
                .body("status", is(SC_OK))
                .body("success", is(true))
                .body("message", is(USER_PROFILE_SUCCESS.message))
                .body("data.email", is(user.getEmail()))
                .body("data.name", is(user.getName()))
                .body("data.id", is(user.getId()));
    }

    @Test
    @DisplayName("Test the response in Get method in profile endpoint when the token is invalid.")
    void getProfileInvalidToken() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setToken("123Token");
        ValidatableResponse response = new UsersClient().getProfile(user.getToken());
        response.statusCode(SC_UNAUTHORIZED)
                .body("status", is(SC_UNAUTHORIZED))
                .body("success", is(false))
                .body("message", is(USER_UNAUTHORIZED.message));
    }
}
