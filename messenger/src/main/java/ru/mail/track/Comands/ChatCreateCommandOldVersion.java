package ru.mail.track.Comands;

import ru.mail.track.Data.Chat;
import ru.mail.track.Stores.MessageStore;
import ru.mail.track.Session.Session;

public class ChatCreateCommandOldVersion implements Command {
    private MessageStore messageStore;

    public ChatCreateCommandOldVersion(MessageStore messageStore){
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
                String[] strings = new String[2];
                strings[0] = "The chat already exists";
                strings[1] = id.toString();
                if (messageStore.getChatsByUserId(aLong).contains(id)) ;
                    return new Result(Result.Status.Success, strings);
            } else {
                Chat chat = new Chat();
                for (int i = 1; i < args.length; i++) {
                    try {
                        chat.addParticipant(Long.parseLong(args[i]));
                    } catch (NumberFormatException e) {
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
