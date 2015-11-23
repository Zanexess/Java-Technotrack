package ru.mail.track.Comands;

import ru.mail.track.Stores.MessageStore;
import ru.mail.track.Session.Session;

import java.util.List;


public class ChatListCommand implements Command {

    private MessageStore messageStore;
    public ChatListCommand(MessageStore messageStore){
        this.messageStore = messageStore;
    }
    public Result execute(Session session, String[] args) {
        if (session.isUserAuthentificated()) {
            Long lg = session.getSessionUser().getId();
            List<Long> chats = messageStore.getChatsByUserId(lg);
            String[] strings = new String[chats.size()];
            int i = 0;
            for (Long id : chats){
                strings[i] = id.toString();
                i++;
            }
            return new Result(Result.Status.Success, strings);
        }
        return new Result(Result.Status.LoginError, "User not log in");
    }
}
