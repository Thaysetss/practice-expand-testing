package com.practice.expandingtesting.functional.users;

import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.MessagesData.USERS_REGISTER_NULL_NAME;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class ProfilePatchTests {

    @Test
    @DisplayName("Test the patch of name and password")
    void patchAllUserData() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setName("New Name in Patch");
        user.setPassword("PatchTest123");
        new UsersClient().patchProfile(user)
                .statusCode(SC_OK)
                .body("data.name", is(user.getName()));
    }

    @Test
    @DisplayName("Test the patch with name with less than the minimum characters.")
    void patchInvalidName() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setName("New");
        user.setPassword("PatchTest123");
        new UsersClient().patchProfile(user)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("status", is(SC_BAD_REQUEST))
                .body("message", is(USERS_REGISTER_NULL_NAME.message));
    }

    @Test
    @DisplayName("Test the patch with invalid token")
    void patchInvalidToken() {
        UserModel user = new UserUtils().AuthenticationNewUser();
        user.setToken("15156");
        new UsersClient().patchProfile(user)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("status", is(SC_BAD_REQUEST));
    }
}