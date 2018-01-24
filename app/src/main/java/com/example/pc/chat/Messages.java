package com.example.pc.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Messages extends AppCompatActivity {

    ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        final Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        final String username = bundle.getString("Username");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference data_reference = database.getReference("Messages/");
        final Button input_btn = findViewById(R.id.input_button);

        final EditText input_et = findViewById(R.id.input_message);
        input_et.requestFocus();


        input_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText input_et = findViewById(R.id.input_message);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = df.format(c.getTime());

                String random = String.valueOf(Calendar.getInstance().getTimeInMillis());


                data_reference.child(random).child("time").setValue(time);
                data_reference.child(random).child("text").setValue(input_et.getText().toString());
                data_reference.child(random).child("username").setValue(username);

                input_et.setText("");
            }
        });

        data_reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String time = dataSnapshot.child("time").getValue(String.class);
                String text = dataSnapshot.child("text").getValue(String.class);
                String username = dataSnapshot.child("username").getValue(String.class);

                if (text == null && username == null) {
                    messages.add(new Message(time, dataSnapshot.getKey()));
                }
                else {
                    messages.add(new Message(username, text, time, dataSnapshot.getKey()));
                    update();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                String username = dataSnapshot.child("username").getValue(String.class);
                String time = dataSnapshot.child("time").getValue(String.class);
                String text = dataSnapshot.child("text").getValue(String.class);
                String id = dataSnapshot.getKey();

                for (int i = 0; i < messages.size(); i++) {
                    if (messages.get(i).getId().equals(id)) {
                        messages.get(i).setText(text);
                        messages.get(i).setTime(time);
                        messages.get(i).setUsername(username);
                    }
                }

                if (text != null && username != null)
                    update();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Message message = new Message(dataSnapshot.child("username").getValue().toString(), dataSnapshot.child("text").getValue().toString(), dataSnapshot.child("time").getValue().toString(), dataSnapshot.getKey().toString());

                for (Message messageCheck : messages) {
                    if (message.getId().equals(messageCheck.getId()))
                        messages.remove(messages.indexOf(messageCheck));
                }

                update();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void update()
    {
        final MessagesAdapter adapter = new MessagesAdapter(messages);
        final RecyclerView chat = findViewById(R.id.message_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        chat.setLayoutManager(layoutManager);
        chat.setItemAnimator(new DefaultItemAnimator());
        chat.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

}
