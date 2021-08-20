package com.example.Tripapp.Notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.Tripapp.R;

import java.util.ArrayList;
import java.util.List;

public class ShowNoteAdapter extends ArrayAdapter<String> {


    private Context _context;
    private ArrayList<String> _note;

    public ShowNoteAdapter(@NonNull Context context, ArrayList<String> note) {
        super(context, R.layout.show_notes_list, R.id.show_note, note);
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
            view = inflater.inflate(R.layout.show_notes_list, parent, false);
            viewHolder = new  ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = ( ViewHolder) view.getTag();
        }

        viewHolder.getNote().setText(getItem(position));

        return view ;
    }

    private class ViewHolder {
        View convertView;
        TextView note;

        public ViewHolder(View view) {
            convertView = view;
        }

        public TextView getNote() {
            if (note == null)
                note = (TextView) convertView.findViewById(R.id.show_note);
            return note;

        }
    }
}
