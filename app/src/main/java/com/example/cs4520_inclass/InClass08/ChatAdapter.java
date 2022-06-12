package com.example.cs4520_inclass.InClass08;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4520_inclass.R;

import java.util.ArrayList;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    String mUserEmail;
    ArrayList<Message> messages;

    public ChatAdapter(ArrayList<Message> messages, String mUserEmail) {
        this.messages = messages;
        this.mUserEmail = mUserEmail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRecyclerView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.chat_message_row, parent, false);

        return new ChatAdapter.ViewHolder(itemRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message curMessage = messages.get(position);

        TextView messageText = holder.getMessageText();
        messageText.setText(curMessage.toString());

        // Hopefully this will move your messages to the right and their messages to the left.
        // Its currently inellegant and hacky, but oh well.
        if (curMessage.getSenderEmail().equals(mUserEmail)) {
            messageText.setPadding(75, 8, 8, 8);
        } else {
            messageText.setPadding(8, 8, 75, 8);
        }
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
