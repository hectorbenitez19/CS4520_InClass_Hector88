package com.example.cs4520_inclass.InClass08;

import java.util.ArrayList;

public class Chat {

    private ArrayList<Message> Message;

    public Chat(ArrayList<Message> chat) {
        this.Message = chat;
    }

    public Chat() {
    }

    public ArrayList<Message> getMessage() {
        return Message;
    }

    public void setMessage(ArrayList<Message> message) {
        this.Message = message;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chat=" + Message +
                '}';
    }



}
