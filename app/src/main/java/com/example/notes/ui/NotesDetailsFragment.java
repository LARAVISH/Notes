package com.example.notes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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

        Toolbar toolbar = view.findViewById(R.id.tool_bar_details);
        toolbar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_delete:
                    Toast.makeText(requireContext(), getString(R.string.delete), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_send:
                    Toast.makeText(requireContext(), getString(R.string.send), Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });

        if (requireContext() instanceof ToolBarHolder) {
            ((ToolBarHolder) requireActivity()).setToolBar(toolbar);
        }

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
        title.setOnClickListener(view1 -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), view);
            requireActivity().getMenuInflater().inflate(R.menu.menu_pop_up, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(requireContext(), "search", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_copy:
                        Toast.makeText(requireContext(), "copy", Toast.LENGTH_SHORT).show();
                        return true;
                }

                return false;
            });
            popupMenu.show();
        });

    }

    private void showNote(Notes note) {
        title.setText(note.getName());
        icon.setImageResource(note.getIcon());
    }
}
