package com.example.pc.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends AppCompatActivity {

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

                    sleep(Constants.splashTimeMillis);
                    Intent intent = new Intent(getApplicationContext(), UsernameActivity.class);
                    startActivity(intent);
                    finish();


                } catch (Exception e)
                {
                    Intent intent = new Intent(getApplicationContext(), UsernameActivity.class);
                    startActivity(intent);
                    finish();
                }
            }


        };

        thread.start();






    }
}
