package com.example.pc.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Username extends AppCompatActivity {

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

                Intent intent = new Intent(getApplicationContext(),Messages.class);
                intent.putExtra("Username", usernameEdit.getText().toString() );
//                intent.putExtra("Messages", getIntent().getExtras().getSerializable("Messages"));
                startActivity(intent);

            }
        });
    }

}
