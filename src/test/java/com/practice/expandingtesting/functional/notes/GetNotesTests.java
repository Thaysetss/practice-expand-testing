package com.practice.expandingtesting.functional.notes;

import com.practice.expandingtesting.client.notes.NotesClient;
import com.practice.expandingtesting.client.users.UsersClient;
import com.practice.expandingtesting.factory.NotesFactory;
import com.practice.expandingtesting.model.NotesModel;
import com.practice.expandingtesting.model.UserModel;
import com.practice.expandingtesting.utils.UserUtils;
import org.junit.jupiter.api.*;

import java.time.ZoneOffset;
import java.util.List;

import static com.practice.expandingtesting.data.NotesMessages.GET_NOTES_SUCCESS;
import static java.time.LocalDate.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetNotesTests {
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
    @DisplayName("Get all notes successfully when the user has one note.")
    void testGetAllNotesSuccess() {
        NotesModel notes = new NotesFactory().generateRandomNotes();
        new NotesClient().postNotes(this.user, notes);
        List<NotesModel> responseNotes = new NotesClient().getAllNotes(this.user)
                .statusCode(SC_OK)
                .body("success", is(true),
                        "status", is(SC_OK),
                        "message", is(GET_NOTES_SUCCESS.message))
                .extract()
                .body()
                .jsonPath().getList("data", NotesModel.class);
        assertThat(responseNotes.size(), is(1));
        assertFalse(responseNotes.get(0).getId().isEmpty());
        assertThat(responseNotes.get(0).getTitle(), is(notes.getTitle()));
        assertThat(responseNotes.get(0).getDescription(), is(notes.getDescription()));
        assertThat(responseNotes.get(0).getCategory(), is(notes.getCategory()));
        assertTrue(responseNotes.get(0).getCreated_at().contains(now(ZoneOffset.UTC).toString()));
        assertTrue(responseNotes.get(0).getUpdated_at().contains(now(ZoneOffset.UTC).toString()));
        assertFalse(responseNotes.get(0).getUser_id().isEmpty());
    }

    @Test
    @DisplayName("Get all notes successfully when the user has more than one note.")
    void testGetMoreThanOneNoteSuccess() {

        NotesModel notes = new NotesFactory().generateRandomNotes();
        new NotesClient().postNotes(this.user, notes);

        NotesModel secondNote = new NotesFactory().generateRandomNotes();
        new NotesClient().postNotes(this.user, secondNote);

        List<NotesModel> responseNotes = new NotesClient().getAllNotes(this.user)
                .statusCode(SC_OK)
                .body("success", is(true),
                        "status", is(SC_OK),
                        "message", is(GET_NOTES_SUCCESS.message))
                .extract()
                .body()
                .jsonPath().getList("data", NotesModel.class);

        assertThat(responseNotes.size(), is(2));
        assertFalse(responseNotes.get(1).getId().isEmpty());
        assertThat(responseNotes.get(1).getTitle(), is(notes.getTitle()));
        assertThat(responseNotes.get(1).getDescription(), is(notes.getDescription()));
        assertThat(responseNotes.get(1).getCategory(), is(notes.getCategory()));
        assertTrue(responseNotes.get(1).getCreated_at().contains(now(ZoneOffset.UTC).toString()));
        assertTrue(responseNotes.get(1).getUpdated_at().contains(now(ZoneOffset.UTC).toString()));
        assertFalse(responseNotes.get(1).getUser_id().isEmpty());

        assertFalse(responseNotes.get(0).getId().isEmpty());
        assertThat(responseNotes.get(0).getTitle(), is(secondNote.getTitle()));
        assertThat(responseNotes.get(0).getDescription(), is(secondNote.getDescription()));
        assertThat(responseNotes.get(0).getCategory(), is(secondNote.getCategory()));
        assertTrue(responseNotes.get(0).getCreated_at().contains(now(ZoneOffset.UTC).toString()));
        assertTrue(responseNotes.get(0).getUpdated_at().contains(now(ZoneOffset.UTC).toString()));
        assertFalse(responseNotes.get(0).getUser_id().isEmpty());
    }
}
