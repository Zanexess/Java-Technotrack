package ru.mail.track.comands;

import ru.mail.track.data.DataStorage;
import ru.mail.track.data.Message;
import ru.mail.track.session.Session;

import java.util.List;

/**
 * Отобразить историю
 */
public class HistoryCommand implements Command {
    DataStorage dataStorage;

    public HistoryCommand(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public Result execute(Session session, String[] args) {
        if (args.length == 1) {
            List<Message> history = dataStorage.getHistory(Integer.MAX_VALUE);
            if (history != null)
                for (Message message: history) {
                    System.out.println(message.getMessage());
                }
            return new Result(Result.Status.Success);
        } else {
            if (args.length == 2) {
                try {
                    List<Message> history = dataStorage.getHistory(Integer.parseInt(args[1]));
                    if (history != null) {
                        for (Message message : history) {
                            System.out.println(message.getMessage());
                        }
                    }
                    return new Result(Result.Status.Success);
                } catch (NumberFormatException e) {
                    System.out.println("Wrong parameters");
                    return new Result(Result.Status.InvalidInput);
                }
            }
        }
        return new Result(Result.Status.Error);
    }
}
