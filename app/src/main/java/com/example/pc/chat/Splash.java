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
}
