package ru.mail.track.Comands;

import java.util.Map;

import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Session.Session;

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

        data[0] = "\t\\login <Username> <Password>";
        data[1] = "\t\\exit";
        data[2] = "\t\\register <Username> <Password>";
        data[3] = "\t\\info <int id>";
        data[4] = "\t\\help";
        data[5] = "\t\\user <nickname>";
        data[6] = "\t\\user_pass <old_password> <new_password>";
        data[7] = "\t\\chat_list";
        data[8] = "\t\\chat_create <userId list>";
        data[9] = "\t\\chat_send <int id> <Text>";
        data[10] = "\t\\chat_history";
        data[11] = "\t\\chat_find <int id> <regex>";

        return new Result(Result.Status.Success, data);
    }
}
