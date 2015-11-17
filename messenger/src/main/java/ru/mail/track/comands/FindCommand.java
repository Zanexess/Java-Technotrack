package ru.mail.track.comands;

import ru.mail.track.data.DataStorage;
import ru.mail.track.data.Message;
import ru.mail.track.session.Session;

import java.util.List;

/**
 * Created by zanexess on 17.10.15.
 */
public class FindCommand implements Command {
    DataStorage dataStorage;

    public FindCommand(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public Result execute(Session session, String[] args) {
        if (args.length != 2) {
            return new Result(Result.Status.InvalidInput, "Find must have 2 arguments; Example \\find word");
        } else {
            List<Message> history = dataStorage.getHistory(-1);
            for (Message message: history) {
                String[] tokens = message.getMessage().split(" ");
                for (String temp: tokens)
                    if (temp != null && temp.equals(args[1])) {
                        System.out.println("Word " + args[1] + " found at Message "
                                + message.getMessage());
                    }
            }
        }
        return new Result(Result.Status.Success);
    }
}
