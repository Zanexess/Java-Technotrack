package ru.mail.track.comands;

import ru.mail.track.data.MessageStore;
import ru.mail.track.session.Session;

import java.util.List;
import java.util.LongSummaryStatistics;


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
                //Не особо верно, но будет работать
                try {
                    aLong = Long.parseLong(args[1]);
                } catch (NumberFormatException e){
                    return new Result(Result.Status.InvalidInput, "Parsing problem");
                }
                //TODO Исправить
                List<Long> messages = messageStore.getMessagesFromChat(aLong);
                System.out.println(messages.size());
                StringBuilder builder = new StringBuilder();
                for (Long id : messages){
                    System.out.println(id);
                    builder.append(messageStore.getMessageById(id).getMessage() + "\n");
                }
                String str = builder.toString();
                return new Result(Result.Status.Success, str);
            }
        }
        return new Result(Result.Status.LoginError, "You are not log in");
    }
}
