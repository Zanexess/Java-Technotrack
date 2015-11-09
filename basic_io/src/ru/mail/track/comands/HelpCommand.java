package ru.mail.track.comands;

import java.util.Map;

import ru.mail.track.Messeges.MessageType;
import ru.mail.track.session.Session;

/**
 * Помощь
 */
public class HelpCommand implements Command {

    private Map<MessageType, Command> commands;

    public HelpCommand(Map<MessageType, Command> commands) {
        this.commands = commands;
    }

    public Result execute(Session session, String[] args) {
        String data[] = new String[commands.size()+1];
        data[0] = "Commands: ";
        int i = 1;
        for (Map.Entry entry: commands.entrySet()) {
            data[i] = "\t" + entry.getKey();
            i++;
        }
        return new Result(Result.Status.Success, data);
    }
}
