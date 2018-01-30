package com.example.pc.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UsernameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button usernameCofirm = findViewById(R.id.username_btn);
        final EditText usernameEdit = findViewById(R.id.username_et);

        usernameCofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(usernameEdit.getText().toString().length()>=3) {
                    Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
                    intent.putExtra("UsernameActivity", usernameEdit.getText().toString());
                    startActivity(intent);
                }
                else
                    Toast.makeText(UsernameActivity.this, "Username needs to be at least 3 characters long", Toast.LENGTH_SHORT).show();

            }
        });
    }

}