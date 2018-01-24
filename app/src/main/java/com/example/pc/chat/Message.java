package com.example.pc.chat;


import java.io.Serializable;

/**
 * Created by PC on 1/23/2018.
 * Ovo znaš i sam šta je
 */

public class Message implements Serializable {

    private String text;
    private String time;
    private String username;
    private String id;

     Message(String username,String text,String time,String id)
    {
        this.username=username;
        this.text=text;
        this.time=time;
        this.id = id;
    }

    Message()
    {

    }

    void setText(String text)
    {
        this.text = text;
    }

    String getText()
    {
        return this.text;
    }

    void setTime(String time)
    {
        this.time = time;
        /*
        Date date, Context context
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context)

        Date date = new Date(location.getTime());
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        mTimeText.setText("Time: " + dateFormat.format(date));
         */
    }

    String getTime()
    {
        return this.time;
    }

    void setUsername(String username)
    {
        this.username=username;
    }

    String getUsername()
    {
        return this.username;
    }

    void setId(String id) { this.id=id;}

    String getId() { return  this.id;}
}
