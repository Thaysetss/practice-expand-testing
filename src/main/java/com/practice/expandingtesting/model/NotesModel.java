package com.practice.expandingtesting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class NotesModel {
    private String id;
    private String title;
    private String description;
    private String category;
    private String completed;
    private String created_at;
    private String updated_at;
    private String user_id;
}
