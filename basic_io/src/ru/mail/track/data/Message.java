package ru.mail.track.data;

import java.util.Date;

public class Message {
    private static long id = 0;
    private Long MessageId;
    private String message;
    private Long senderId;
    private Long chatId;
    //private Long timestamp;
    private String timestamp;


    public Message(){

    }

    public Message(String message) {
        this.message = message;
        id++;
        MessageId = id;
        timestamp = new Date(System.currentTimeMillis()).toString();
        //timestamp = System.currentTimeMillis()/1000;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Message(Long senderId, String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId= " + senderId + '\'' +
                ", message=" + message + '\'' +
                ", timestamp=" + timestamp + '\'' +
                '}';
    }
}