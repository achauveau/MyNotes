package com.example.amandine.mynotes;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author achauveau
 */
public class NoteActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etContent;

    private String noteFileName;
    private Notes loadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        etTitle = findViewById(R.id.note_et_title);
        etContent = findViewById(R.id.note_et_content);

        noteFileName = getIntent().getStringExtra("NOTE_FILE");
        if(noteFileName!=null && !noteFileName.isEmpty()){
            loadedNote = Utilities.getNoteByName(this,noteFileName);
            if(loadedNote!=null){
                etTitle.setText(loadedNote.getTitle());
                etContent.setText(loadedNote.getContent());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_note_save :
                saveNote();
                break;
            case R.id.action_note_delete :
                deleteNote();
                break;
        }
        return true;
    }

    private void saveNote(){
        Notes note;
        if(etTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Tu pourrais au moins lui donner un titre avant de l'enregistrer...",Toast.LENGTH_SHORT).show();
            return;
        }
        if(loadedNote==null){
            note = new Notes(System.currentTimeMillis(), etTitle.getText().toString(), etContent.getText().toString());
        }else{
            note = new Notes(loadedNote.getDateTime(), etTitle.getText().toString(), etContent.getText().toString());
        }
        if(Utilities.saveNote(this, note)){
            Toast.makeText(this,"La note a bien été enregistrée !",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Je n'ai pas réussi à enregistrer ta note... As-tu assez de place ?",Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void deleteNote(){
        if(loadedNote==null){
            finish();
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Suppression")
                    .setMessage("Veux-tu vraiment supprimer ta note \""+etTitle.getText().toString()+"\" ?")
                    .setPositiveButton("OUI!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utilities.deleteNote(getApplicationContext(), loadedNote.getDateTime()+Utilities.FILE_EXTENSION );
                            Toast.makeText(getApplicationContext(),"La note a été supprimée",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("NON!!",null)
                    .setCancelable(false);
            dialog.show();
        }
    }
}
