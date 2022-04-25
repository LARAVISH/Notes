package com.example.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Notes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private OnNoteClicked onNoteClicked;
    private final List<Notes> notes = new ArrayList<>();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM HH:mm", Locale.getDefault());

    public void setOnNoteClicked(OnNoteClicked onNoteClicked) {
        this.onNoteClicked = onNoteClicked;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        Notes notes1 = notes.get(position);
        holder.title.setText(notes1.getName());
        holder.message.setText(notes1.getMessage());
        holder.date.setText(simpleDateFormat.format(notes1.getCurrentDate()));


    }

    public void setDate(Collection<Notes> date) {
        notes.addAll(date);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    interface OnNoteClicked {
        void onNoteClicked(Notes note);

    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView title, message, date;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);

            itemView.findViewById(R.id.card_view).setOnClickListener(view -> {
                if (onNoteClicked != null) {
                    int clickedPosition = getAdapterPosition();
                    onNoteClicked.onNoteClicked(notes.get(clickedPosition));
                }

            });
        }

    }
}
