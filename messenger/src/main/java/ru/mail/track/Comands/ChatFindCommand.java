package ru.mail.track.Comands;

import ru.mail.track.Stores.MessageStore;
import ru.mail.track.Session.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChatFindCommand implements Command {

    private MessageStore messageStore;
    private Long aLong;
    private Matcher m;
    private Pattern p;

    public ChatFindCommand(MessageStore messageStore){
        this.messageStore = messageStore;
    }

    public Result execute(Session session, String[] args) {
        if (session.isUserAuthentificated()){
            if (args.length < 3){
                return new Result(Result.Status.InvalidInput,
                        "Format of command: \\chat_find <int id> <regex>");
            } else {
                try {
                    aLong = Long.parseLong(args[1]);
                } catch (NumberFormatException e){
                    return new Result(Result.Status.InvalidInput, "Parsing problem");
                }

                Long lg = session.getSessionUser().getId();
                List<Long> chats = messageStore.getChatsByUserId(lg);
                if (chats.contains(aLong)) {
                    List<Long> messages = messageStore.getMessagesFromChat(aLong);
                    p = Pattern.compile(args[2]);
                    List<String> strings = new ArrayList<>();
                    for (Long id : messages) {
                        m = p.matcher(messageStore.getMessageById(id).getMessage().substring(1));
                        int counter = 0;
                        while (m.find()) {
                            counter++;
                        }
                        if (counter > 0) {
                            strings.add(messageStore.getMessageById(id).getMessage());
                        }
                    }
                    if (strings.size() != 0) {
                        String[] result = new String[strings.size()];
                        strings.toArray(result);
                        return new Result(Result.Status.Success, result);
                    } else {
                        return new Result(Result.Status.Success, "Nothing was found");
                    }
                } else {
                    return new Result(Result.Status.Error, "You not a participant of this chat");
                }
            }
        }
        return new Result(Result.Status.LoginError, "You are not log in");
    }
}
