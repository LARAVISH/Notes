package com.example.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        for (Notes note : notes) {

            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);

            itemView.findViewById(R.id.root).setOnClickListener
                    (view1 -> {
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(SELECT_NOTE, note);
                            getParentFragmentManager()
                                    .setFragmentResult(NOTE_KEY, bundle);


                        } else {
                            NoteDetailsActivity.show(requireContext(), note);
                        }

                    });

            ImageView icon = itemView.findViewById(R.id.icon_1);
            icon.setImageResource(note.getIcon());
            TextView title = itemView.findViewById(R.id.title_1);
            title.setText(note.getName());
            container.addView(itemView);

        }
    }
}
