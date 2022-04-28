package com.example.notes.ui;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.di.Dependencies;
import com.example.notes.domain.Callback;
import com.example.notes.domain.Notes;

import java.util.List;

public class NotesListFragment extends Fragment {


    private Notes selectedNote;
    private int selectedPosition;

    private NotesAdapter adapter;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notesList = view.findViewById(R.id.notes_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext()
                , LinearLayoutManager.VERTICAL, false);
        notesList.setLayoutManager(layoutManager);


        adapter = new NotesAdapter(this);
        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Notes note) {


                Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoteLongClicked(Notes notes, int clickedPosition) {
                selectedNote = notes;
                selectedPosition = clickedPosition;
            }

        });

        notesList.setAdapter(adapter);

        getParentFragmentManager()
                .setFragmentResultListener(AddNoteBottomSheetDialogFragment.ADD_KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Notes note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

                        int index =adapter.addNote(note);

                        adapter.notifyItemInserted(index);

                        notesList.smoothScrollToPosition(index);
                    }
                });

        getParentFragmentManager()
                .setFragmentResultListener(AddNoteBottomSheetDialogFragment.UPDATE_KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Notes note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

                        adapter.replaceNote(note, selectedPosition);

                        adapter.notifyItemChanged(selectedPosition);
                    }
                });

        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteBottomSheetDialogFragment.addInstance()
                        .show(getParentFragmentManager(), "AddNoteBottomSheetDialogFragment");
            }
        });

        progressBar = view.findViewById(R.id.progress);

        progressBar.setVisibility(View.VISIBLE);

        Dependencies.NOTES_REPOSITORY.getAll(new Callback<List<Notes>>() {
            @Override
            public void onSuccess(List<Notes> data) {
                adapter.setData(data);

                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError(Throwable exception) {

                progressBar.setVisibility(View.GONE);

            }
        });
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_notes_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:

                progressBar.setVisibility(View.VISIBLE);

                Dependencies.NOTES_REPOSITORY.removeNote(selectedNote, new Callback<Void>() {
                    @Override
                    public void onSuccess(Void data) {

                        progressBar.setVisibility(View.GONE);

                        adapter.removeNote(selectedNote);

                        adapter.notifyItemRemoved(selectedPosition);
                    }

                    @Override
                    public void onError(Throwable exception) {

                    }
                });

                return true;

            case R.id.action_edit:
                AddNoteBottomSheetDialogFragment.editInstance(selectedNote)
                        .show(getParentFragmentManager(), "AddNoteBottomSheetDialogFragment");
                return true;
        }
        return super.onContextItemSelected(item);
    }
}




