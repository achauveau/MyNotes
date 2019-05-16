package com.example.amandine.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author achauveau
 */
public class MainActivity extends AppCompatActivity {

    private ListView notesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesListView = findViewById(R.id.main_listview_notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_main_newnote:
                //start NoteActivity in NewNote mode
                Intent newNoteActivity = new Intent(this,NoteActivity.class);
                startActivity(newNoteActivity);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesListView.setAdapter(null);

        ArrayList<Notes> notes = Utilities.getAllSavedNotes(this);

        if(notes==null || notes.size()==0){
            Toast.makeText(this,"Tu n'as pas encore enregistré de note",Toast.LENGTH_SHORT).show();
            return;
        }else{
            NoteAdapter na = new NoteAdapter(this, R.layout.item_notes, notes);
            notesListView.setAdapter(na);

            notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fname = ((Notes)notesListView.getItemAtPosition(position)).getDateTime()+Utilities.FILE_EXTENSION;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE", fname);
                    startActivity(viewNoteIntent);
                }
            });
        }

    }
}
