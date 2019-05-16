package com.example.amandine.mynotes;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author achauveau
 */
public class Notes implements Serializable {

    private long dateTime;
    private String title;
    private String content;

    public Notes(long dateTime, String title, String content) {
        this.dateTime = dateTime;
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getFormattedDateTime(Context context){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        df.setTimeZone(TimeZone.getDefault());
        return df.format(new Date(dateTime)); //formatted date
    }
}
