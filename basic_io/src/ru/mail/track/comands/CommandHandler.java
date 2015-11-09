package ru.mail.track.comands;

import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.network.MessageListener;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.util.Map;

public class CommandHandler implements MessageListener {

    Map<MessageType, Command> commands;
    private Result result;
    private MessageBase msg;

    public CommandHandler(Map<MessageType, Command> commands) {
        this.commands = commands;
    }

    public void onMessage(Session session, MessageBase message) {
        Command cmd = commands.get(message.getMessageType());
        if (cmd != null) {
            result = cmd.execute(session, message.getArgs());
            if (result.getStatus() == Result.Status.Success) {
                msg = new MessageBase(MessageType.SRV_SUCCESS, result.getInfo());
            } else if (result.getStatus() == Result.Status.Error) {
                msg = new MessageBase(MessageType.SRV_ERROR, result.getInfo());
            } else if (result.getStatus() == Result.Status.LoginError) {
                msg = new MessageBase(MessageType.SRV_LOGINERROR, result.getInfo());
            } else if (result.getStatus() == Result.Status.InvalidInput) {
                msg = new MessageBase(MessageType.SRV_INVALIDINPUT, result.getInfo());
            } else if (result.getStatus() == Result.Status.InvalidInput) {
                msg = new MessageBase(MessageType.SRV_LOGINSUCCESS, result.getInfo());
            }
        } else {
            String[] info = new String[1];
            info[0] = "Invalid Input";
            msg = new MessageBase(MessageType.SRV_ERROR, info);
        }
        try {
            session.getConnectionHandler().send(msg);
        } catch (IOException e){
            System.out.println("IOException");
        }
    }
}