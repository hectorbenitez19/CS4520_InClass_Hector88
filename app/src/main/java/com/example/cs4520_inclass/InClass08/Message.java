package com.example.cs4520_inclass.InClass08;

public class Message {

    private String senderEmail;
    private String messageText;

    public Message(String senderEmail, String messageText) {
        this.senderEmail = senderEmail;
        this.messageText = messageText;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return this.messageText;
    }
}
