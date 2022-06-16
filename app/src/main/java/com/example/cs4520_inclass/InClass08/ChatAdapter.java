package com.example.cs4520_inclass.InClass08;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4520_inclass.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

//Hector Benitez InClass Assignment 8

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    String mUserEmail;
    ArrayList<HashMap> messages;
    public static final int RIGHT_MESSAGE = 1;
    public static final int LEFT_MESSAGE = -1;

    public void setMessages(ArrayList<HashMap> messages) {
        this.messages = messages;
    }

    public ChatAdapter(ArrayList<HashMap> messages, String mUserEmail) {
        this.messages = messages;
        this.mUserEmail = mUserEmail;
    }

    // Returns RIGHT_MESSAGE if this user sent the message, or LEFT_MESSAGE if they didn't.
    // Used to lean the messages left or right.
    @Override
    public int getItemViewType (int position) {
        assert messages != null;
        assert mUserEmail != null;

        if (messages.get(position).get(InClass08.SENDER_KEY).equals(mUserEmail)) {
            return RIGHT_MESSAGE;
        } else {
            return LEFT_MESSAGE;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutID;
        if (viewType == RIGHT_MESSAGE) {
            layoutID = R.layout.chat_message_row_right;
        } else if (viewType == LEFT_MESSAGE) {
            layoutID = R.layout.chat_message_row_left;
        } else {
            throw new IllegalArgumentException("Invalid viewType given: " + viewType + ". viewType "
            + "should be either RIGHT_MESSAGE or LEFT_MESSAGE.");
        }

        View itemRecyclerView = LayoutInflater
                .from(parent.getContext())
                .inflate(layoutID, parent, false);

        return new ChatAdapter.ViewHolder(itemRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> curMessage = messages.get(position);

        TextView messageText = holder.getMessageText();
        //messageText.setText(curMessage.toString());

        messageText.setText(curMessage.get(InClass08.MESSAGE_TEXT_KEY));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView messageText;

        public TextView getMessageText() {
            return messageText;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.messageText = itemView.findViewById(R.id.a8_chatMessageTextView);
        }
    }
}
