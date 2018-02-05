package com.example.pc.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MessagesActivity extends AppCompatActivity {

    RecyclerView chat;
    ArrayList<Message> messages = new ArrayList<>();
    ProgressBar progressBar;
    Button input_btn;
    String username;
    String message, messageCheck;
    MessagesAdapter adapter = new MessagesAdapter(messages);
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout messages_containter;
    boolean isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        final Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle bundle = getIntent().getExtras();

        isFirstTime = true;
        progressBar= findViewById(R.id.messages_progress_bar);
        chat = findViewById(R.id.message_recycler_view);
        input_btn = findViewById(R.id.input_button);
        username = bundle.getString("UsernameActivity");
        messages_containter = findViewById(R.id.relative_layout_messages);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference data_reference = database.getReference("Messages/");
        final EditText input_et = findViewById(R.id.input_message);


        username = username.toLowerCase(); //ZA SLUCAJ DA SU RAZLICITO VELIKA I MALA SLOVA
        username = username.substring(0,1).toUpperCase()+username.substring(1);


        //SLANJE PORUKE
        input_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //DA NE BI MOGLA PRAZNA PORUKA

                message = input_et.getText().toString();
                messageCheck = message.trim().replaceAll("\n", "");
                messageCheck = message.trim().replaceAll(" ", "");


                if (messageCheck=="")
                    input_et.setText("");

                else {

                    while(message.endsWith("\n")) {
                        message = message.substring(0,message.length()-2);
                        input_et.setText("radi");
                    }

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = df.format(c.getTime());
                    String random = String.valueOf(Calendar.getInstance().getTimeInMillis());

                    data_reference.child(random).child("time").setValue(time);
                    data_reference.child(random).child("text").setValue(message);
                    data_reference.child(random).child("username").setValue(username);

                    input_et.setText("");

                }

                input_et.requestFocus();
            }
        });

        input_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chat.scrollToPosition(messages.size() - 1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        

//TODO : URADITI DA SE PROGRESS PALI SAMO PRI ULASKU U ACITIVITY, NAPRAVITI LJEPSI PROGRESS +
        //TODO : KAD SE OTVORI TASTATURA DA PORUKE IDU GORE
        //TODO : KAD SE OTVORI VISE REDOVA EDITTEXTA DA PORUKE IDU GORE

        //UCITAVANJE PODATAKA
        data_reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(isFirstTime) {
                    chat.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }



                String time = dataSnapshot.child("time").getValue(String.class);
                String text = dataSnapshot.child("text").getValue(String.class);
                String usernameMessage = dataSnapshot.child("username").getValue(String.class);

                int type;

                if (text == null && username == null) {
                    messages.add(new Message(time, dataSnapshot.getKey()));
                }
                else {
                    if(username.equals(usernameMessage)) type = 2;
                    else type = 1;
                    messages.add(new Message(usernameMessage, text, time, dataSnapshot.getKey()));
                    update(type);
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
                Message check;

                for (int iterator = 0; iterator < messages.size();iterator++){
                    check = messages.get(iterator);
                    if(check.getId().equals(message.getId())) {
                        messages.remove(messages.indexOf(check));
                        break;
                    }
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

    //OSVJEZAVANJE PORUKA
    public void update(int type) {

        layoutManager = new LinearLayoutManager(getApplicationContext());
        chat.setLayoutManager(layoutManager);
        chat.setItemAnimator(new DefaultItemAnimator());
        adapter.onCreateViewHolder(chat, 1);
        chat.setAdapter(adapter);
        layoutManager.scrollToPosition(messages.size() - 1);

        input_btn.requestFocus();
        if (isFirstTime) {
            progressBar.setVisibility(View.INVISIBLE);
            chat.setVisibility(View.VISIBLE);
        }

        isFirstTime = false;
    }

    public void update()
    {
        layoutManager = new LinearLayoutManager(getApplicationContext());
        chat.setLayoutManager(layoutManager);
        chat.setItemAnimator(new DefaultItemAnimator());
        chat.setAdapter(adapter);
        layoutManager.scrollToPosition(messages.size()-1);

        input_btn.requestFocus();
        if(isFirstTime){
            progressBar.setVisibility(View.INVISIBLE);
            chat.setVisibility(View.VISIBLE);
        }

        isFirstTime=false;
    }

}




