package com.example.pc.chat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    Thread thread;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar =  findViewById(R.id.splash_screen_progress_bar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference  = database.getReference("Messages/");

        thread = new Thread() {
            @Override
            public void run() {

                try {

                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(), Username.class);
                    startActivity(intent);
                    finish();
                    /*
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                         //   new LoadingMessages().execute(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }); */

                } catch (Exception e)
                {

                    Intent intent = new Intent(getApplicationContext(), Username.class);
                    startActivity(intent);
                    finish();
                }
            }


        };

        thread.start();




    }
    public class LoadingMessages extends AsyncTask <DataSnapshot, Integer, ArrayList<Message>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Message> messages) {
            super.onPostExecute(messages);

            Intent intent = new Intent(getApplicationContext(), Username.class);
            intent.putExtra("Messages",messages);
            startActivity(intent);
            finish();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onCancelled(ArrayList<Message> messages) {
            super.onCancelled(messages);
        }

        @Override
        protected ArrayList<Message> doInBackground(DataSnapshot... dataSnapshots) {

            ArrayList<Message> messages = new ArrayList<>();

            int progress = 0;

            for(DataSnapshot d : dataSnapshots[0].getChildren())
            {
                Message message = new Message("","","","");
                message.setUsername(d.child("username").getValue().toString());
                message.setTime(d.child("time").getValue().toString());
                message.setText(d.child("text").getValue().toString());
                message.setId(d.getKey().toString());

                messages.add(message);

                progress+=Math.round(100/d.getChildrenCount());
                publishProgress(progress);

            }
                return messages;
        }
    }
}
