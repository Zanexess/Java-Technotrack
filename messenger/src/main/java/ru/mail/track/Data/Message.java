package ru.mail.track.Data;

import ru.mail.track.Dao.Identified;

import java.util.Date;

public class Message implements Identified {
    private Long messageId;
    private String message;
    private Long senderId;
    private Long chatId;
    private String timestamp;

    public Message(){

    }

    public Message(String message) {
        this.message = message;
        timestamp = new Date(System.currentTimeMillis()).toString();
    }

    public Message (String message, Long senderId, Long chatId, Long messageId){
        this.message = message;
        this.senderId = senderId;
        this.chatId = chatId;
        this.messageId = messageId;
    }

    public Message (String message, Long senderId, Long chatId, String timestamp){
        this.message = message;
        this.senderId = senderId;
        this.chatId = chatId;
        this.timestamp = timestamp;
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

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", message='" + message + '\'' +
                ", senderId=" + senderId +
                ", chatId=" + chatId +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public Long getId() {
        return messageId;
    }

    @Override
    public void setId(Long id) {
        this.messageId = id;
    }
}