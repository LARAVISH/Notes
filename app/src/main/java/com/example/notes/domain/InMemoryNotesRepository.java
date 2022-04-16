package com.example.notes.domain;

import android.content.Context;

import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNotesRepository implements NotesRepository {

    private static NotesRepository INSTANCE;
    private final Context context;
    private final List<Notes> notes;

    private InMemoryNotesRepository(Context context) {
        this.context = context;
        notes = new ArrayList<>();
    }

    public static NotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryNotesRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public List<Notes> getAll() {

        notes.add(new Notes(context.getString(R.string.at_home), R.drawable.ic_baseline_home));
        notes.add(new Notes(context.getString(R.string.on_work), R.drawable.ic_baseline_work));
        notes.add(new Notes(context.getString(R.string.shopping), R.drawable.ic_baseline_shopping));
        notes.add(new Notes(context.getString(R.string.call), R.drawable.ic_baseline_phone));
        notes.add(new Notes(context.getString(R.string.major_event), R.drawable.ic_baseline_event));

        return notes;
    }

    @Override
    public void add(Notes note) {

    }
}
