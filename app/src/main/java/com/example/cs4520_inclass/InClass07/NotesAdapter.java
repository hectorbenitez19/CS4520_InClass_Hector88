package com.example.cs4520_inclass.InClass07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4520_inclass.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    ArrayList<String> notes;
    IAdapterToNotes mListener;

    public NotesAdapter(ArrayList<String> notes, Context context) {
        this.notes = notes;
        if(context instanceof NotesAdapter.IAdapterToNotes){
            this.mListener = (IAdapterToNotes) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement IAdapterToNotes");
        }
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }


    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRecyclerView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.notes_row, parent, false);

        return new NotesAdapter.ViewHolder(itemRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        String curNote = notes.get(position);

        holder.getNoteDisplay().setText(curNote);

        holder.getToEditButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.getToDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteDisplay;
        private final Button edit;
        private final Button delete;

        public TextView getNoteDisplay() {
            return noteDisplay;
        }

        public Button getToEditButton() {
            return edit;
        }

        public Button getToDeleteButton() {return delete; }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.noteDisplay = itemView.findViewById(R.id.a7_notesRow_note);
            this.edit = itemView.findViewById(R.id.a7_notesRow_editButton);
            this.delete = itemView.findViewById(R.id.a7_notesRow_deleteButton);
        }
    }

    public interface IAdapterToNotes {

    }
}
