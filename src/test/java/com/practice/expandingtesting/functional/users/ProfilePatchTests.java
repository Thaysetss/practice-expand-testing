package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.USERS_REGISTER_NULL_NAME;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class ProfilePatchTests {

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
    @DisplayName("Test the patch of name and password")
    void patchAllUserData() {
        this.user.setName("New Name in Patch");
        this.user.setPassword("PatchTest123");
        new UsersClient().patchProfile(this.user)
                .statusCode(SC_OK)
                .body("data.name", is(this.user.getName()));
    }

    @Test
    @DisplayName("Test the patch with name with less than the minimum characters.")
    void patchInvalidName() {
        this.user.setName("New");
        this.user.setPassword("PatchTest123");
        new UsersClient().patchProfile(this.user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("status", is(SC_BAD_REQUEST))
                .body("message", is(USERS_REGISTER_NULL_NAME.message));
    }

    @Test
    @DisplayName("Test the patch with invalid token")
    void patchInvalidToken() {
        this.user.setToken("15156");
        new UsersClient().patchProfile(this.user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_UNAUTHORIZED));
    }
}