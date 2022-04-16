package com.example.notes.domain;

import java.util.List;

public interface NotesRepository {

    List<Notes> getAll();

    void add(Notes note);

}
