package ru.mail.track;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import ru.mail.track.comands.Command;
import ru.mail.track.data.DataStorage;
import ru.mail.track.session.Session;

public class InputHandler {

    private Session session;

    private Map<String, Command> commandMap;

    private DataStorage dataStorage;

    public InputHandler(Session session, Map<String, Command> commandMap, DataStorage dataStorage) {
        this.session = session;
        this.commandMap = commandMap;
        this.dataStorage = dataStorage;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void handle(String data) {
        if (data.startsWith("\\")) {
            String[] tokens = data.split(" ");

            Command cmd = commandMap.get(tokens[0]);
            cmd.execute(session, tokens);
        } else {
            if (session.getSessionUser() == null) {
                System.out.println(">" + data);
            } else {
                dataStorage.addMessage(data + " | " + getDateTime() + "\n");
                System.out.println("<" + data);
            }
        }
    }

}



