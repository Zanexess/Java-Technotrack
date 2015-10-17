package ru.mail.track.comands;

import ru.mail.track.data.DataStorage;
import ru.mail.track.session.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zanexess on 17.10.15.
 */
public class FindCommand implements Command {
    DataStorage dataStorage;

    public FindCommand(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public Result execute(Session session, String[] args) {
        if (args.length != 2) {
            return new Result(-1);
        } else {
            List<String> history = dataStorage.getHistory(-1);
            for (String str: history) {
                String[] tokens = str.split(" ");
                for (String temp: tokens)
                    if (temp != null && temp.equals(args[1])) {
                        System.out.println("Word " + args[1] + " found at string: " + str);
                    }
            }
        }
        return new Result(1);
    }
}
