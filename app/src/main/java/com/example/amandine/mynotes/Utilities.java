package com.example.amandine.mynotes;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author achauveau
 */
public class Utilities {

    public static final String FILE_EXTENSION = ".bin";

    /**
     * Save the note in a private storage area of the user
     * @param context
     * @param note
     * @return
     */
    public static boolean saveNote(Context context, Notes note){

        String fname = String.valueOf(note.getDateTime())+FILE_EXTENSION;

        FileOutputStream fout;
        ObjectOutputStream oout;

        try {
            fout = context.openFileOutput(fname, context.MODE_PRIVATE);
            oout = new ObjectOutputStream(fout);
            oout.writeObject(note);
            fout.close();
            oout.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static ArrayList<Notes> getAllSavedNotes(Context context){
        ArrayList<Notes> notes = new ArrayList<>();
        File fdir = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        for(String file : fdir.list()){
            if(file.endsWith(FILE_EXTENSION)){
                noteFiles.add(file);
            }
        }

        FileInputStream fin;
        ObjectInputStream oin;

        for(int i=noteFiles.size()-1 ; i>=0 ; i--){
            try{
                fin = context.openFileInput(noteFiles.get(i));
                oin = new ObjectInputStream(fin);
                notes.add((Notes)oin.readObject());
                fin.close();
                oin.close();
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return notes;
    }

    public static Notes getNoteByName(Context context, String fname){
        File file = new File(context.getFilesDir(), fname);

        if(file.exists()){
            FileInputStream fin;
            ObjectInputStream oin;
            Notes note;
            try {
                fin = context.openFileInput(fname);
                oin = new ObjectInputStream(fin);

                note = (Notes)oin.readObject();

                fin.close();
                oin.close();
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            return note;
        }
        return null;
    }

    public static boolean deleteNote(Context context, String fname){
        File dir = context.getFilesDir();
        File file = new File(dir, fname);

        if(file.exists()){
            file.delete();
            return true;
        }
        return false;
    }
}
