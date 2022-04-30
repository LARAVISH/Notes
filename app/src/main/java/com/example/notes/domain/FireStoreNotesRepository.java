package com.example.notes.domain;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FireStoreNotesRepository implements NotesRepository {

    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_CURRENT_DATE = "currentDare";
    private static final String NOTES = "notes";
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void getAll(Callback<List<Notes>> callback) {

        firebaseFirestore.collection(NOTES)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Notes> result = new ArrayList<>();

                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        String id = documentSnapshot.getId();

                        String title = documentSnapshot.getString(KEY_TITLE);
                        String message = documentSnapshot.getString(KEY_MESSAGE);
                        Date createdAt = documentSnapshot.getDate(KEY_CURRENT_DATE);

                        result.add(new Notes(id, title, message, createdAt));
                    }

                    callback.onSuccess(result);

                });
    }

    @Override
    public void addNote(String title, String message, Callback<Notes> callback) {
        HashMap<String, Object> data = new HashMap<>();

        Date createdAt = new Date();

        data.put(KEY_TITLE, title);
        data.put(KEY_MESSAGE, message);
        data.put(KEY_CURRENT_DATE, createdAt);

        firebaseFirestore.collection(NOTES)
                .add(data)
                .addOnSuccessListener(documentReference -> callback.onSuccess(new Notes(documentReference.getId(), title, message, createdAt)));

    }

    @Override
    public void removeNote(Notes note, Callback<Void> callback) {
        firebaseFirestore.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(callback::onSuccess);

    }

    @Override
    public void updateNote(Notes note, String title, String message, Callback<Notes> callback) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(KEY_TITLE, title);
        data.put(KEY_MESSAGE, message);

        firebaseFirestore.collection(NOTES)
                .document(note.getId())
                .update(data)
                .addOnSuccessListener(unused -> {

                    Notes result = new Notes(note.getId(), title, message, note.getCurrentDate());

                    callback.onSuccess(result);
                });
    }
}
