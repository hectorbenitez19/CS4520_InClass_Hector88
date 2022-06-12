package com.example.cs4520_inclass.InClass08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4520_inclass.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<String> users;
    IchatUserClicked mListener;

    public UserAdapter(ArrayList<String> users, Context context) {
        this.users = users;
        if(context instanceof IchatUserClicked){
            this.mListener = (IchatUserClicked) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement IchatUserClicked");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRecyclerView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.chat_user_row, parent, false);



        return new ViewHolder(itemRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String curUser = users.get(position);

        holder.getUsernameDisplay().setText(curUser);

        holder.getToChatButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.openChat(curUser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView usernameDisplay;
        private final ImageButton toChatButton;

        public TextView getUsernameDisplay() {
            return usernameDisplay;
        }

        public ImageButton getToChatButton() {
            return toChatButton;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.usernameDisplay = itemView.findViewById(R.id.a8_chatUserRowText);
            this.toChatButton = itemView.findViewById(R.id.a8_goToChatButton);
        }
    }

    public interface IchatUserClicked {
        void openChat(String email);
    }
}
