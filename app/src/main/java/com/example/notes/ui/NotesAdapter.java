package com.example.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Notes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM, HH:mm", Locale.getDefault());
    private final List<Notes> data = new ArrayList<>();
    private final Fragment fragment;
    private OnNoteClicked noteClicked;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setData(Collection<Notes> notes) {
        data.addAll(notes);
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    public int addNote(Notes note) {
        data.add(note);

        return data.size() - 1;
    }

    public void removeNote(Notes selectedNote) {
        data.remove(selectedNote);
    }

    public void replaceNote(Notes note, int selectedPosition) {
        data.set(selectedPosition, note);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        Notes note = data.get(position);

        holder.title.setText(note.getName());
        holder.message.setText(note.getMessage());

        holder.date.setText(simpleDateFormat.format(note.getCurrentDate()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    interface OnNoteClicked {
        void onNoteClicked(Notes note);

        void onNoteLongClicked(Notes note, int position);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView message;
        TextView date;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);

            CardView cardView = itemView.findViewById(R.id.card_view);

            fragment.registerForContextMenu(cardView);

            cardView.setOnClickListener(view -> {
                if (noteClicked != null) {
                    int clickedPosition = getAdapterPosition();
                    noteClicked.onNoteClicked(data.get(clickedPosition));
                }
            });

            cardView.setOnLongClickListener(view -> {
                cardView.showContextMenu();

                if (noteClicked != null) {
                    int clickedPosition = getAdapterPosition();

                    noteClicked.onNoteLongClicked(data.get(clickedPosition), clickedPosition);
                }

                return true;
            });

        }
    }
}
