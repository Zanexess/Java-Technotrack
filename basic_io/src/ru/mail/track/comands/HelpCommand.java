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
        String data[] = new String[12];
        data[0] = "Commands: ";

        data[1] = "\t\\login <Username> <Password>";
        data[2] = "\t\\logout";
        data[3] = "\t\\register <Username> <Password>";
        data[4] = "\t\\info <int id>";
        data[5] = "\t\\help";
        data[6] = "\t\\user <int id>";
        data[7] = "\t\\user_pass <old_password> <new_password>";
        data[8] = "\t\\chat_list";
        data[9] = "\t\\chat_create <userId list>";
        data[10] = "\t\\chat_send <Text>";
        data[11] = "\t\\chat_history";

        return new Result(Result.Status.Success, data);
    }
}
