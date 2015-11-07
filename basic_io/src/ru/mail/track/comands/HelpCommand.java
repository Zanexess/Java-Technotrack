package ru.mail.track.comands;

import java.util.Map;

import ru.mail.track.session.Session;

/**
 * Помощь
 */
public class HelpCommand implements Command {

    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public Result execute(Session session, String[] args) {
        System.out.println("Commands:");
        for (Map.Entry entry: commands.entrySet()) {
            System.out.println("\t" + entry.getKey());
        }
        return new Result(Result.Status.Success);
    }
}
