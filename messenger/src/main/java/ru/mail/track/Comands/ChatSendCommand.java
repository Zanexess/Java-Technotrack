package ru.mail.track.Comands;

import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Data.Message;
import ru.mail.track.Stores.MessageStore;
import ru.mail.track.Session.SessionManager;
import ru.mail.track.Session.Session;

import java.io.IOException;
import java.util.List;

public class ChatSendCommand implements Command {
    private MessageStore messageStore;
    private SessionManager sessionManager;
    //private long messagesCounter = 1;

    public ChatSendCommand(SessionManager sessionManager, MessageStore messageStore){
        this.messageStore = messageStore;
        this.sessionManager = sessionManager;
    }

    public Result execute(Session session, String[] args) {
        if (session.isUserAuthentificated()){
            if (args.length == 1){
                return new Result(Result.Status.InvalidInput,
                        "Format of command: \\chat_send <id> <message>");
            } else {
                Long aLong;

                try {
                   aLong  = Long.parseLong(args[1]);
                } catch (NumberFormatException e) {
                    return new Result(Result.Status.InvalidInput, "Parsing problem");
                }

                if (messageStore.getChatsByUserId(session.getSessionUser().getId()).contains(aLong)){
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < args.length; i++){
                        builder.append(" " + args[i]);
                    }
                    String str = builder.toString();

                    //messageStore.getChatById(aLong).addMessage(messagesCounter);

                    Message msg = new Message(str, session.getSessionUser().getId(), aLong, new java.util.Date().toString());

                    messageStore.addMessage(msg);
                    //messagesCounter++;

                    String[] string = new String[1];
                    string[0] = msg.getMessage();

                    MessageBase mm = new MessageBase(MessageType.SRV_NEWMESSAGE, string, msg);


                    List<Long> parts = messageStore.getParticipantByChatId(aLong);
                    //Chat chat = messageStore.getChatById(aLong);
                    //List<Long> parts = chat.getParticipantIds();
                    try {
                        for (Long userId: parts) {
                            Session userSession = sessionManager.getSessionByUser(userId);
                            if (userSession != null){
                                //TODO FIX
                                System.out.println("1");
                                userSession.getConnectionHandler().send(mm);
                                System.out.println("7");
                            } else {

                            }
                        }
                        return new Result(Result.Status.Success, "SEND");
                    } catch (IOException e){
                        //return new Result(Result.Status.Error, "");
                    }
                } else {
                    return new Result(Result.Status.Error, "The chat doesn't exist");
                }
            }
        }
        return new Result(Result.Status.LoginError, "You are not log in");
    }
}
