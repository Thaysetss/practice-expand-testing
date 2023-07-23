package com.practice.expandingtesting.factory;

import com.practice.expandingtesting.model.NotesModel;
import net.datafaker.Faker;

public class NotesFactory {

    private final Faker faker = new Faker();

    public NotesModel generateRandomNotes() {
        NotesModel notes = new NotesModel();
        notes.setTitle(faker.backToTheFuture().character());
        notes.setCategory("Home");
        notes.setDescription(faker.lorem().maxLengthSentence(200));
        return notes;
    }

    public NotesModel generateNullTitleNotes() {
        NotesModel notes = new NotesModel();
        notes.setTitle(null);
        notes.setCategory("Home");
        notes.setDescription(faker.lorem().maxLengthSentence(200));
        return notes;
    }

    public NotesModel generateNullDescriptionNotes() {
        NotesModel notes = new NotesModel();
        notes.setTitle(faker.babylon5().character());
        notes.setCategory("Home");
        notes.setDescription(null);
        return notes;
    }
    public NotesModel generateNullCategoryNotes() {
        NotesModel notes = new NotesModel();
        notes.setTitle(faker.babylon5().character());
        notes.setCategory(null);
        notes.setDescription(faker.lorem().maxLengthSentence(200));
        return notes;
    }
}
