package com.practice.expandingtesting.user;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.*;

public class UserRegisterTests {
    @Test
    @DisplayName("Test add new user successfully with random data.")
    void createNewRandomDataUser() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewRandomUser(user)
                .statusCode(SC_CREATED)
                .body("data.id", is(not(empty())))
                .body("data.name", is(user.getName()))
                .body("data.email", is(user.getEmail()))
                .body("success", is(true))
                .body("status", is(SC_CREATED))
                .body("message", is(USER_REGISTER_ACCOUNT_CREATED.message));
    }

    @Test
    @DisplayName("Test adding new user with invalid email.")
    void createNewUserInvalidEmail() {
        var user = new UserFactory().generateUserInvalidEmail();
        new UsersClient().postRegisterNewRandomUser(user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is(USER_REGISTER_INVALID_EMAIL.message));
    }

    @Test
    @DisplayName("Test adding new user with null name.")
    void createNewUserNullName() {
        var user = new UserFactory().generateUserNullName();
        new UsersClient().postRegisterNewRandomUser(user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is(USER_REGISTER_NULL_NAME.message));
    }

    @Test
    @DisplayName("Test adding new user with null email.")
    void createNewUserNullEmail() {
        var user = new UserFactory().generateUserNullEmail();
        new UsersClient().postRegisterNewRandomUser(user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is(USER_REGISTER_INVALID_EMAIL.message));
    }

    @Test
    @DisplayName("Test adding new user with null password.")
    void createNewUserNullPassword() {
        var user = new UserFactory().generateUserNullPassword();
        new UsersClient().postRegisterNewRandomUser(user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is(USER_REGISTER_NULL_PASSWORD.message));
    }
}