package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.InMemoryNotesRepository;
import com.example.notes.domain.Notes;

import java.util.List;

public class NotesListFragment extends Fragment {


    public static final String NOTE_KEY = "NOTE_KEY";
    public static final String SELECT_NOTE = "SELECT_NOTE";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Notes> notes = InMemoryNotesRepository.getInstance(requireContext()).getAll();
        LinearLayout container = view.findViewById(R.id.container_1);

        Toolbar toolbar = view.findViewById(R.id.tool_bar_list);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_search:
                    Toast.makeText(requireContext(), getString(R.string.search), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_notify:
                    Toast.makeText(requireContext(), getString(R.string.notify), Toast.LENGTH_SHORT).show();
                    return true;
            }

            return false;
        });

        if (requireContext() instanceof ToolBarHolder) {
            ((ToolBarHolder) requireActivity()).setToolBar(toolbar);
        }

        for (Notes note : notes) {

            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);

            itemView.findViewById(R.id.root).setOnClickListener
                    (view1 -> getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, NotesDetailsFragment.newInstance(note))
                            .addToBackStack("details")
                            .commit());

            ImageView icon = itemView.findViewById(R.id.icon_1);
            icon.setImageResource(note.getIcon());
            TextView title = itemView.findViewById(R.id.title_1);
            title.setText(note.getName());
            container.addView(itemView);

        }
    }
}
