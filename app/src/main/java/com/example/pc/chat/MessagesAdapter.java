package com.example.pc.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by PC on 1/23/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder>{


        private ArrayList<Message> messages;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView title, text, time;

            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.item_title);
                text = view.findViewById(R.id.item_text);
                time = view.findViewById(R.id.item_time);
            }



        }

            public MessagesAdapter ( ArrayList<Message> messages)
            {
                this.messages = messages;
            }

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.single_message_item, parent, false);

                View itemView2 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.single_message_item_user, parent, false);

                if(viewType ==1)
                    return new MyViewHolder(itemView);
                else
                    return new MyViewHolder(itemView2);
            }


            @Override
            public void onBindViewHolder(final MyViewHolder holder, int position) {
                Message message = messages.get(position);

                holder.title.setText(message.getUsername());
                holder.time.setText(message.getTime());
                holder.text.setText(message.getText());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }


}
