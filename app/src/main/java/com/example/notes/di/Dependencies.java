package com.example.notes.di;

import com.example.notes.domain.FireStoreNotesRepository;
import com.example.notes.domain.NotesRepository;

public class Dependencies {

    private static final NotesRepository NOTES_REPOSITORY = new FireStoreNotesRepository();

    public static NotesRepository getNotesRepository() {
        return NOTES_REPOSITORY;
    }
}
