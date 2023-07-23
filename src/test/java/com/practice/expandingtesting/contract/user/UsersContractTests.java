package com.practice.expandingtesting.contract.user;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.UserFactory;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class UsersContractTests {

    @Test
    @DisplayName("Test contract after register a new user.")
    void usersRegisterContractTest() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewUser(user)
                .statusCode(SC_CREATED)
                .body(matchesJsonSchemaInClasspath("jsonschemas/UserRegisterSchema.json"));
    }

    @Test
    @DisplayName("Test contract after login a new user.")
    void usersLoginContractTest() {
        var user = new UserFactory().generateRandomUser();
        new UsersClient().postRegisterNewUser(user);
        new UsersClient().postLogin(user)
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("jsonschemas/UserLoginSchema.json"));

    }

    @Test
    @DisplayName("Test the patch contract when the update was successful.")
    void patchUserContractTest() {
        UserModel user = new UserUtils().authenticationNewUser();
        user.setName("New Name in Patch");
        user.setPassword("PatchTest123");
        new UsersClient().patchProfile(user)
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("jsonschemas/UserRegisterSchema.json"));
    }

    @Test
    @DisplayName("Validate the contract of the Get method in profile endpoint.")
    void getProfileContractTest() {
        UserModel user = new UserUtils().authenticationNewUser();
        new UsersClient().getProfile(user)
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("jsonschemas/UserRegisterSchema.json"));
    }

    @Test
    @DisplayName("Test contract for forgot password endpoint")
    void forgotPasswordContractTest() {
        UserModel user = new UserUtils().authenticationNewUser();
        new UsersClient().postForgotPassword(user)
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("jsonschemas/ChangePasswordSchema.json"));
    }

    @Test
    @DisplayName("Test the contract of the delete-account method.")
    void deleteAccountContractTest() {
        UserModel user = new UserUtils().authenticationNewUser();
        new UsersClient().deleteAccount(user)
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("jsonschemas/DeleteSchema.json"));
    }

    @Test
    @DisplayName("Test the contract of the delete-account method.")
    void deleteLogoutContractTest() {
        UserModel user = new UserUtils().authenticationNewUser();
        new UsersClient().deleteLogout(user)
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("jsonschemas/DeleteSchema.json"));
    }
}
