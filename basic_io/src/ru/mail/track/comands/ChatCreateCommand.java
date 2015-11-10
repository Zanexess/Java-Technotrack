package ru.mail.track.comands;

import ru.mail.track.data.Chat;
import ru.mail.track.data.MessageStore;
import ru.mail.track.session.Session;

import java.util.List;

/**
 * Created by zanexess on 10.11.15.
 */
public class ChatCreateCommand implements Command {
    private MessageStore messageStore;

    public ChatCreateCommand(MessageStore messageStore){
        this.messageStore = messageStore;
    }

    public Result execute(Session session, String[] args) {
        if (session.isUserAuthentificated()){
            if (args.length == 1) {
                return new Result(Result.Status.InvalidInput,
                        "Format of command: \\chat_create <Participant id list>");
            } else if (args.length == 2) {
                Long id = Long.parseLong(args[1]);
                Long aLong = session.getSessionUser().getId();
                if (messageStore.getChatsByUserId(aLong).contains(id)) ;
                    return new Result(Result.Status.Success, "The chat already exists");
            } else {
                Chat chat = new Chat();
                for (int i = 1; i < args.length; i++) {
                    try {
                        chat.addParticipant(Long.parseLong(args[i]));
                    } catch (Exception e) {
                        return new Result(Result.Status.InvalidInput, "Parsing Wrong");
                    }
                }
                messageStore.addChat(chat);
                return new Result(Result.Status.Success, "Chat successfully created!");
            }
        }
        return new Result(Result.Status.LoginError, "You are not log in");
    }
}
