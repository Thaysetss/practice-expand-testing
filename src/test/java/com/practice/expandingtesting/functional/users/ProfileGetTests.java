package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.USERS_PROFILE_SUCCESS;
import static com.practice.expandingtesting.data.MessagesData.USERS_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class ProfileGetTests {

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
    @DisplayName("Validate the Get method in profile endpoint when the user is valid.")
    void getProfileValidLogin() {
        new UsersClient().getProfile(this.user)
                .statusCode(SC_OK)
                .body("status", is(SC_OK))
                .body("success", is(true))
                .body("message", is(USERS_PROFILE_SUCCESS.message))
                .body("data.email", is(this.user.getEmail()))
                .body("data.name", is(this.user.getName()))
                .body("data.id", is(this.user.getId()));
    }

    @Test
    @DisplayName("Test the response in Get method in profile endpoint when the token is invalid.")
    void getProfileInvalidToken() {
        this.user.setToken("123Token");
        new UsersClient().getProfile(this.user)
                .statusCode(SC_UNAUTHORIZED)
                .body("status", is(SC_UNAUTHORIZED))
                .body("success", is(false))
                .body("message", is(USERS_UNAUTHORIZED.message));
    }
}
