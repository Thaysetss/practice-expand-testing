package com.practice.expandingtesting.functional.notes;

import com.practice.expandingtesting.client.notes.NotesClient;
import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.NotesFactory;
import com.practice.expandingtesting.model.NotesModel;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practice.expandingtesting.data.NotesMessages.*;
import static com.practice.expandingtesting.data.UsersMessages.UNAUTHORIZED;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;

public class PostNotesTests {

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
    @DisplayName("Test post a new random note successfully.")
    void postNotesRandomData() {
        NotesModel notes = new NotesFactory().generateRandomNotes();
        new NotesClient().postNotes(this.user, notes)
                .statusCode(SC_OK)
                .body("success", is(true),
                        "status", is(SC_OK),
                        "message", is(POST_CREATED_SUCCESS.message),
                        "data.id", Matchers.is(Matchers.not(empty())),
                        "data.title", is(notes.getTitle()),
                        "data.description", is(notes.getDescription()),
                        "data.category", is(notes.getCategory()),
                        "data.completed", is(false),
                        "data.user_id", is(this.user.getId()));
    }

    @Test
    @DisplayName("Test post a new random note with null title.")
    void postNoteWithoutTitle() {
        NotesModel notes = new NotesFactory().generateNullTitleNotes();
        new NotesClient().postNotes(this.user, notes)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false),
                        "status", is(SC_BAD_REQUEST),
                        "message", is(TITLE_INVALID_CHARACTERS.message));
    }

    @Test
    @DisplayName("Test post a new random note with null description.")
    void postNoteWithoutDescription() {
        NotesModel notes = new NotesFactory().generateNullDescriptionNotes();
        new NotesClient().postNotes(this.user, notes)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false),
                        "status", is(SC_BAD_REQUEST),
                        "message", is(DESCRIPTION_INVALID_CHARACTERS.message));
    }

    @Test
    @DisplayName("Test post a new random note with null category.")
    void postNoteWithoutCategory() {
        NotesModel notes = new NotesFactory().generateNullCategoryNotes();
        new NotesClient().postNotes(this.user, notes)
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false),
                        "status", is(SC_BAD_REQUEST),
                        "message", is(CATEGORY_INVALID_VALUE.message));
    }

    @Test
    @DisplayName("Test post a note using an invalid token")
    void postNotesInvalidToken() {
        NotesModel notes = new NotesFactory().generateRandomNotes();
        this.user.setToken("TEST010203TOken");
        new NotesClient().postNotes(this.user, notes)
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false),
                        "status", is(SC_UNAUTHORIZED),
                        "message", is(UNAUTHORIZED.message));
    }
}