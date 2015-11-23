package ru.mail.track.Comands;

import ru.mail.track.Stores.MessageStore;
import ru.mail.track.Session.Session;

import java.util.List;


public class ChatHistoryCommand implements Command {

    private MessageStore messageStore;

    public ChatHistoryCommand(MessageStore messageStore){
        this.messageStore = messageStore;
    }

    public Result execute(Session session, String[] args) {
        if (session.isUserAuthentificated()){
            if (args.length == 1){
                return new Result(Result.Status.InvalidInput,
                        "Format of command: \\chat_history <id>");
            } else {
                Long aLong;
                try {
                    aLong = Long.parseLong(args[1]);
                } catch (NumberFormatException e){
                    return new Result(Result.Status.InvalidInput, "Parsing problem");
                }

                Long lg = session.getSessionUser().getId();
                List<Long> chats = messageStore.getChatsByUserId(lg);

                if (chats.contains(aLong)) {
                    List<Long> messages = messageStore.getMessagesFromChat(aLong);
                    StringBuilder builder = new StringBuilder();
                    for (Long id : messages) {
                        builder.append(messageStore.getMessageById(id).getMessage() + "\n");
                    }
                    String str = builder.toString();
                    return new Result(Result.Status.Success, str);
                } else {
                    return new Result(Result.Status.Error, "You can't get history of this chat, " +
                            "because you not the participant of it");
                }
            }
        }
        return new Result(Result.Status.LoginError, "You are not log in");
    }
}
