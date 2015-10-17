package ru.mail.track.comands;

import ru.mail.track.data.DataStorage;
import ru.mail.track.session.Session;

/**
 * Отобразить историю
 */
public class HistoryCommand implements Command {
    DataStorage dataStorage;

    public HistoryCommand(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public Result execute(Session session, String[] args) {
        if (args.length == 1) {
            dataStorage.printHistory(dataStorage.getHistory(-1));
            return new Result(1);
        } else {
            if (args.length == 2) {
                try {
                    dataStorage.printHistory(dataStorage.getHistory(Integer.parseInt(args[1])));
                    return new Result(1);
                } catch (NumberFormatException e) {
                    System.err.println("Wrong parameters");
                }
            }
        }
        return new Result(-1);
    }
}
