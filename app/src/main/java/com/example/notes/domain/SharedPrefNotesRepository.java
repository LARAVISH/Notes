package com.example.notes.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SharedPrefNotesRepository implements NotesRepository {
    private static final String KEY_SAVED_NOTES = "KEY_SAVED_NOTES";

    private final SharedPreferences sharedPreferences;

    public SharedPrefNotesRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("NOTES", Context.MODE_PRIVATE);
    }

    @Override
    public void getAll(Callback<List<Notes>> callback) {
        Gson gson = new Gson();

        String savedData = sharedPreferences.getString(KEY_SAVED_NOTES, "[]");

        Type type = new TypeToken<ArrayList<Notes>>() {
        }.getType();

        List<Notes> savedNotes = gson.fromJson(savedData, type);


        callback.onSuccess(savedNotes);

    }

    @Override
    public void addNote(String title, String message, Callback<Notes> callback) {

        Notes note = new Notes(UUID.randomUUID().toString(), title, message, new Date());

        Gson gson = new Gson();

        String savedData = sharedPreferences.getString(KEY_SAVED_NOTES, "[]");

        Type type = new TypeToken<ArrayList<Notes>>() {
        }.getType();

        List<Notes> savedNotes = gson.fromJson(savedData, type);

        savedNotes.add(note);

        String toWrite = gson.toJson(savedNotes, type);

        sharedPreferences
                .edit()
                .putString(KEY_SAVED_NOTES, toWrite)
                .apply();

        callback.onSuccess(note);
    }

    @Override
    public void removeNote(Notes note, Callback<Void> callback) {

    }

    @Override
    public void updateNote(Notes note, String title, String message, Callback<Notes> callback) {

    }
}