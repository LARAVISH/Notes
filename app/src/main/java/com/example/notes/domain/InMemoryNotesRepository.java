package com.example.notes.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InMemoryNotesRepository implements NotesRepository {


    private final List<Notes> date = new ArrayList<>();

    public InMemoryNotesRepository() {

        for (int i = 0; i < 20; i++) {
            date.add(new Notes(UUID.randomUUID().toString(), "заметка " + i, "Сообщение " + i, new Date()));
        }
    }

    @Override
    public List<Notes> getAll() {
        return date;
    }

    @Override
    public void add(Notes note) {

    }
}
