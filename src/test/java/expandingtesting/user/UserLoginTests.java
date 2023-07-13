package expandingtesting.user;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.UserFactory;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class UserLoginTests {

    @Test
    @DisplayName("Test login with valid data from user created successfully.")
    void userLoginValidUserCreatedSuccessfully() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewRandomUser(user);
        ValidatableResponse response = new UsersClient().postLogin(user);
        response.statusCode(SC_OK)
                .body("success", is(true))
                .body("status", is(SC_OK))
                .body("message", is(USER_LOGIN_SUCCESS.message))
                .body("data.id", is(not(empty())))
                .body("data.name", is(user.getName()))
                .body("data.email", is(user.getEmail()))
                .body("data.token", is(not(empty())));
    }

    @Test
    @DisplayName("Test login with an invalid password, resulting in error 401.")
    void userLoginInvalidPassword() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewRandomUser(user);
        user.setPassword("wrong_password");
        ValidatableResponse response = new UsersClient().postLogin(user);
        response.statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is(USER_LOGIN_INCORRECT_EMAIL_PASSWORD.message));
    }

    @Test
    @DisplayName("Test login with an email that was not created, resulting in error 401.")
    void userLoginNonexistentEmail() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewRandomUser(user);
        user.setEmail("nonexistent@newemail.com");
        ValidatableResponse response = new UsersClient().postLogin(user);
        response.statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is(USER_LOGIN_INCORRECT_EMAIL_PASSWORD.message));
    }

    @Test
    @DisplayName("Test login with null email and result status code 400.")
    void userLoginNullEmail() {
        var user = new UserFactory().generateUserNullEmail();
        new UsersClient().postRegisterNewRandomUser(user);
        ValidatableResponse response = new UsersClient().postLogin(user);
        response.statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is(USER_REGISTER_INVALID_EMAIL.message));
    }

    @Test
    @DisplayName("Test login with a password with less than six characters, resulting in error 400.")
    void userLoginLessPasswordCharacters() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewRandomUser(user);
        user.setPassword("123pa");
        ValidatableResponse response = new UsersClient().postLogin(user);
        response.statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is(USER_LOGIN_PASSWORD_LESS_CHARACTERS.message));
    }
}