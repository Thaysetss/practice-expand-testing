package com.practice.expandingtesting.data;

public enum NotesMessages {
    POST_CREATED_SUCCESS("Note successfully created"),
    TITLE_INVALID_CHARACTERS("Title must be between 4 and 100 characters"),
    DESCRIPTION_INVALID_CHARACTERS("Description must be between 4 and 1000 characters"),
    CATEGORY_INVALID_VALUE("Category must be one of the categories: Home, Work, Personal");
    public final String message;

    NotesMessages(String message) {
        this.message = message;
    }
}
