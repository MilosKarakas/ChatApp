package com.example.pc.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class Splash extends AppCompatActivity {

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);


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
