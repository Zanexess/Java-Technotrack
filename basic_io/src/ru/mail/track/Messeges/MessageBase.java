package ru.mail.track.Messeges;

import ru.mail.track.data.Message;

import java.io.Serializable;


public class MessageBase implements Serializable {

    private MessageType messageType;
    private String args[];
    MessageBase(){

    }

    public MessageBase(MessageType messageType, String args[]){
        this.messageType = messageType;
        this.args = args;
    }

    public MessageType getMessageType(){
        return messageType;
    }

    public String[] getArgs(){
        return args;
    }
}
