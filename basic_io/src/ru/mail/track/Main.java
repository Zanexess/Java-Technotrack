package ru.mail.track;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ru.mail.track.authorization.AuthorizationService;
import ru.mail.track.authorization.UserFileStore;
import ru.mail.track.authorization.UserLocalStore;
import ru.mail.track.authorization.UserStore;
import ru.mail.track.comands.*;
import ru.mail.track.data.DataFileStorage;
import ru.mail.track.data.DataStorage;
import ru.mail.track.session.Session;

public class Main {

    private static final String EXIT = "q|exit";

    public static void main(String[] args) {

        Map<String, Command> commands = new HashMap<>();
        Session session = new Session();

        //Нужно добавить файловое хранилище
        UserStore userStore = new UserLocalStore();
        AuthorizationService authService = new AuthorizationService(userStore);
        DataStorage dataStorage = new DataFileStorage(session);

        //Создаем команды
        Command loginCommand = new LoginCommand(authService);
        Command helpCommand = new HelpCommand(commands);
        Command userCommand = new UserCommand();
        Command historyCommand = new HistoryCommand(dataStorage);
        Command sessionInfo = new SessionInfoCommand();
        Command exitProfile = new ExitCommand();
        Command findCommand = new FindCommand(dataStorage);

        commands.put("\\login", loginCommand);       //+
        commands.put("\\help", helpCommand);         //+
        commands.put("\\user", userCommand);         //+
        commands.put("\\history", historyCommand);   //+
        commands.put("\\info", sessionInfo);         //+
        commands.put("\\exit", exitProfile);         //+
        commands.put("\\find", findCommand);         //+


        InputHandler handler = new InputHandler(session, commands, dataStorage);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String line = scanner.nextLine();
                if (line != null && line.matches(EXIT)) {
                    break;
                }
                handler.handle(line);
            } catch (NullPointerException e) {
                System.out.println("Incorrect command!");
            }
        }
    }
}