package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.UsersMessages.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class LoginTests {

    @Test
    @DisplayName("Test login with valid data from user created successfully.")
    void userLoginValidUserCreatedSuccessfully() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewUser(user);
        new UsersClient().postLogin(user)
                .statusCode(SC_OK)
                .body("success", is(true),
                        "status", is(SC_OK),
                        "message", is(LOGIN_SUCCESS.message),
                        "data.id", is(not(empty())),
                        "data.name", is(user.getName()),
                        "data.email", is(user.getEmail()),
                        "data.token", is(not(empty())));
    }

    @Test
    @DisplayName("Test login with an invalid password, resulting in error 401.")
    void userLoginInvalidPassword() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewUser(user);
        user.setPassword("wrong_password");
        new UsersClient().postLogin(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false),
                        "message", is(LOGIN_INCORRECT_EMAIL_PASSWORD.message));
    }

    @Test
    @DisplayName("Test login with an email that was not created, resulting in error 401.")
    void userLoginNonexistentEmail() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewUser(user);
        user.setEmail("nonexistent@newemail.com");
        new UsersClient().postLogin(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false),
                        "message", is(LOGIN_INCORRECT_EMAIL_PASSWORD.message));
    }

    @Test
    @DisplayName("Test login with null email and result status code 400.")
    void userLoginNullEmail() {
        var user = new UserFactory().generateUserNullEmail();
        new UsersClient().postRegisterNewUser(user);
        new UsersClient().postLogin(user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false),
                        "message", is(REGISTER_INVALID_EMAIL.message));
    }

    @Test
    @DisplayName("Test login with a password with less than six characters, resulting in error 400.")
    void userLoginLessPasswordCharacters() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewUser(user);
        user.setPassword("123pa");
        new UsersClient().postLogin(user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false),
                        "message", is(LOGIN_PASSWORD_LESS_CHARACTERS.message));
    }
}