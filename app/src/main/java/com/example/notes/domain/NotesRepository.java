package com.example.notes.domain;

import java.util.List;

public interface NotesRepository {

   void getAll(Callback<List<Notes>> callback);

    void addNote(String title, String message, Callback<Notes> callback);

    void removeNote(Notes note, Callback<Void> callback);

    void updateNote(Notes note, String title, String message, Callback<Notes> callback);

}
