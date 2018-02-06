package com.example.pc.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
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

                otvoriPoruke(usernameEdit);

            }
        });

        //komentar
        usernameEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                    if (i == KeyEvent.KEYCODE_ENTER) {
                        otvoriPoruke(usernameEdit);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void otvoriPoruke(EditText usernameEdit) {

        if(usernameEdit.getText().toString().length()>=Constants.Three) {
            Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
            intent.putExtra(Constants.usernameActivityString, usernameEdit.getText().toString());
            startActivity(intent);
        }
        else
            Toast.makeText(UsernameActivity.this, Constants.usernameTooShort, Toast.LENGTH_SHORT).show();


    }

}
