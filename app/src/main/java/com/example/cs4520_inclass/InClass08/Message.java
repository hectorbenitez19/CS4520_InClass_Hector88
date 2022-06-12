package com.example.cs4520_inclass.InClass08;

import java.util.HashMap;

public class Message {

 //   private String senderEmail;
 //   private String messageText;
    private HashMap<String,String> message;

    public Message(String senderEmail, String messageText) {
 //       this.senderEmail = senderEmail;
  //      this.messageText = messageText;
        this.message = new HashMap<>();
        this.message.put(senderEmail, messageText);
    }

    public Message() {
    }

    public void setMessage(HashMap<String, String> message) {
        this.message = message;
    }

    public HashMap<String, String> getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message=" + message +
                '}';
    }



//     public String getSenderEmail() {
 //       return senderEmail;
 //   }

  //  public void setSenderEmail(String senderEmail) {
   //     this.senderEmail = senderEmail;
   // }

   // public String getMessageText() {
    //    return messageText;
   // }

   // public void setMessageText(String messageText) {
   //     this.messageText = messageText;
   // }

  //  @Override
  //  public String toString() {
   //     return this.messageText;
   // }
}
