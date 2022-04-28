package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;
import com.example.notes.di.Dependencies;
import com.example.notes.domain.Callback;
import com.example.notes.domain.Notes;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNoteBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public static final String ADD_KEY_RESULT = "ADD_KEY_RESULT";
    public static final String UPDATE_KEY_RESULT = "UPDATE_KEY_RESULT";
    public static final String ARG_NOTE = "ARG_NOTE";

    public static AddNoteBottomSheetDialogFragment addInstance() {
        return new AddNoteBottomSheetDialogFragment();
    }

    public static AddNoteBottomSheetDialogFragment editInstance(Notes note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        AddNoteBottomSheetDialogFragment fragment = new AddNoteBottomSheetDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Notes noteToEdit = null;

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            noteToEdit = getArguments().getParcelable(ARG_NOTE);
        }

        EditText title = view.findViewById(R.id.title);
        EditText message = view.findViewById(R.id.message);

        if (noteToEdit != null) {
            title.setText(noteToEdit.getName());
            message.setText(noteToEdit.getMessage());
        }

        Button btnSave = view.findViewById(R.id.save);

        Notes finalNoteToEdit = noteToEdit;
        btnSave.setOnClickListener(view1 -> {

            btnSave.setEnabled(false);

            if (finalNoteToEdit != null) {
                Dependencies.NOTES_REPOSITORY.updateNote(finalNoteToEdit, title.getText().toString(),
                        message.getText().toString(), new Callback<Notes>() {
                            @Override
                            public void onSuccess(Notes data) {

                                Bundle bundle = new Bundle();
                                bundle.putParcelable(ARG_NOTE, data);

                                getParentFragmentManager().setFragmentResult(UPDATE_KEY_RESULT, bundle);

                                btnSave.setEnabled(true);

                                dismiss();

                            }

                            @Override
                            public void onError(Throwable exception) {
                                btnSave.setEnabled(true);
                            }
                        });
            } else {

                Dependencies.NOTES_REPOSITORY.addNote(title.getText().toString(), message.getText().toString(), new Callback<Notes>() {
                    @Override
                    public void onSuccess(Notes data) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ARG_NOTE, data);

                        getParentFragmentManager().setFragmentResult(ADD_KEY_RESULT, bundle);

                        btnSave.setEnabled(true);

                        dismiss();
                    }

                    @Override
                    public void onError(Throwable exception) {

                        btnSave.setEnabled(true);
                    }
                });
            }
        });
    }
}