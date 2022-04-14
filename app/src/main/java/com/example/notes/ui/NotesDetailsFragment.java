package com.example.notes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Notes;

public class NotesDetailsFragment extends Fragment {

    private static final String ARG_EXTRA = "ARG_EXTRA";
    private TextView title;
    private ImageView icon;

    public NotesDetailsFragment() {
        super(R.layout.fragment_note_details);
    }

    public static NotesDetailsFragment newInstance(Notes note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_EXTRA, note);
        NotesDetailsFragment fragment = new NotesDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.title_details);
        icon = view.findViewById(R.id.icon_details);


        getParentFragmentManager()
                .setFragmentResultListener(NotesListFragment.NOTE_KEY, getViewLifecycleOwner(), (requestKey, result) -> {
                    Notes note = result.getParcelable(NotesListFragment.SELECT_NOTE);
                    showNote(note);
                });

        if (getArguments() != null && getArguments().containsKey(ARG_EXTRA)) {
            Notes notes = getArguments().getParcelable(ARG_EXTRA);
            showNote(notes);
        }

    }

    private void showNote(Notes note) {
        title.setText(note.getName());
        icon.setImageResource(note.getIcon());
    }
}
