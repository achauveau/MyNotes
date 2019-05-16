package com.example.amandine.mynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author achauveau
 */
public class NoteAdapter extends ArrayAdapter<Notes> {

    public NoteAdapter(Context context, int resource, ArrayList<Notes> objects) {
        super(context, resource, objects);
    }

    //prepare the view of the note in the listview of the main activity
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_notes, null);
        }

        Notes note = getItem(position);

        if(note != null){
            TextView title = convertView.findViewById(R.id.list_note_title);
            TextView date = convertView.findViewById(R.id.list_note_date);
            TextView content = convertView.findViewById(R.id.list_note_content);

            title.setText(note.getTitle());
            date.setText(note.getFormattedDateTime(getContext()));
            if(note.getContent().length() > 88){
                content.setText(note.getContent().substring(0,88));
            }else{
                content.setText(note.getContent());
            }
        }
        return convertView;
    }
}
