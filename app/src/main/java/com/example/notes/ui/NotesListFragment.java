package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.di.Dependencies;
import com.example.notes.domain.Notes;

import java.util.List;

public class NotesListFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Notes> notes = Dependencies.NOTES_REPOSITORY.getAll();
        RecyclerView recyclerView = view.findViewById(R.id.notes_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()
                , LinearLayoutManager.VERTICAL, false));

        NotesAdapter adapter = new NotesAdapter();

        adapter.setOnNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Notes note) {
                Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        adapter.setDate(notes);
        adapter.notifyDataSetChanged();


    }
}
