package com.example.Tripapp.Notes;


import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.Tripapp.R;

import java.util.ArrayList;


public class NoteAdapter extends ArrayAdapter<String> {

    private Context _context;
    private ArrayList<String> _note;


    public NoteAdapter(@NonNull Context context, ArrayList<String> note) {
        super(context, R.layout.notes_list, R.id.note, note);
        _context = context;
        _note = note;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.notes_list, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.getNote().setText(getItem(position));
        viewHolder.getNew().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_note.size() >= 10) {
                    Toast.makeText(getContext(), "Notes Should be Ten Only", Toast.LENGTH_SHORT).show();
                } else {
                    _note.add("");
                    notifyDataSetChanged();
                }
            }
        });
        viewHolder.getDelete().setOnClickListener(View -> new AlertDialog.Builder(NoteAdapter.super.getContext())
                .setTitle(R.string.delete)
                .setMessage("You Want to Delete This Note")
                .setPositiveButton(R.string.delete, (dialogInterface, i) -> {
                    if (_note.size() == 1) {
                    } else {
                        _note.remove(position);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {

                })
                .show());

        viewHolder.getSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _note.set(position, viewHolder.getNote().getText().toString());
            }
        });

        return view;
    }

    private class ViewHolder {
        View convertView;
        EditText note;
        Button New;
        Button delete;
        ImageButton save;

        public ImageButton getSave() {
            if (save == null)
                save = convertView.findViewById(R.id.save);
            return save;
        }

        public ViewHolder(View view) {
            convertView = view;
        }

        public EditText getNote() {
            if (note == null)
                note = (EditText) convertView.findViewById(R.id.note);
            return note;
        }

        public Button getNew() {
            if (New == null)
                New = (Button) convertView.findViewById(R.id.New);
            return New;
        }

        public Button getDelete() {
            if (delete == null)
                delete = (Button) convertView.findViewById(R.id.delete);
            return delete;
        }
    }
}

