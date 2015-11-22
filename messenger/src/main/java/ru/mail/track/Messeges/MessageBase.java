package ru.mail.track.Messeges;

import ru.mail.track.data.Message;

import java.io.Serializable;
import java.util.Arrays;


public class MessageBase implements Serializable {

    private MessageType messageType;
    private String args[];
    private Message msg;

    MessageBase(){

    }

    public MessageBase(MessageType messageType, String args[]){
        this.messageType = messageType;
        this.args = args;
    }

    public MessageBase(MessageType messageType, String args[], Message msg){
        this.messageType = messageType;
        this.args = args;
        this.msg = msg;
    }

    public MessageType getMessageType(){
        return messageType;
    }

    public String[] getArgs(){
        return args;
    }

    public Message getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "MessageBase{" +
                "messageType=" + messageType +
                ", args=" + Arrays.toString(args) +
                ", msg=" + msg +
                '}';
    }
}
