package com.example.notes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InMemoryNotesRepository implements NotesRepository {


    private final List<Notes> date = new ArrayList<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    private final Handler handler = new Handler(Looper.getMainLooper());

    public InMemoryNotesRepository() {

    }

    @Override
    public void getAll(Callback<List<Notes>> callback) {
        executor.execute(() -> {

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.post(() -> callback.onSuccess(date));
        });

    }

    @Override
    public void addNote(String title, String message, Callback<Notes> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Notes note = new Notes(UUID.randomUUID().toString(), title, message, new Date());

            date.add(note);

            handler.post(() -> callback.onSuccess(note));
        });
    }

    @Override
    public void removeNote(Notes note, Callback<Void> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            date.remove(note);

            handler.post(() -> callback.onSuccess(null));
        });

    }

    @Override
    public void updateNote(Notes note, String title, String message, Callback<Notes> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Notes newNote = new Notes((note.getId()), title, message, note.getCurrentDate());

            int index = date.indexOf(note);

            date.set(index, newNote);

            handler.post(() -> callback.onSuccess(newNote));
        });
    }
}
