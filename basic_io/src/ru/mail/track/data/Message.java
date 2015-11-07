package ru.mail.track.data;

import java.security.Timestamp;

public class Message {
    private String message;
    private Long senderId;
    private Long chatId;
    private Long timestamp;

    public Message(String message) {
        this.message = message;
        timestamp = System.currentTimeMillis()/1000;
    }

    public Long getTimestamp(){
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