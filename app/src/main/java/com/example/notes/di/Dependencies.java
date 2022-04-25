package com.example.notes.di;

import com.example.notes.domain.InMemoryNotesRepository;
import com.example.notes.domain.NotesRepository;

public class Dependencies {
    public static final NotesRepository NOTES_REPOSITORY = new InMemoryNotesRepository();
}
