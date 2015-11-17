package ru.mail.track.data;

import ru.mail.track.session.Session;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * хранилище сообщений в файлах
 */
public class DataFileStorage implements DataStorage {

    private Session session;
    private List<Message> lines;
    private File file;
    private FileWriter writer = null;
    private BufferedReader reader = null;
    private String path;

    public DataFileStorage(Session session) {
        this.session = session;
    }

    @Override
    public void addMessage(Message message) {
        if (session.isUserAuthentificated()) {
            path = session.getSessionUser().getName();
        } else {
            System.out.println("You not log in");
            return;
        }

        try {
            writer = new FileWriter(path, true);
            writer.write(message.getMessage() + " | " + message.getTimestamp() + "\n");
        } catch (FileNotFoundException e) {
            file = new File(path);
            addMessage(message);
        } catch (IOException e) {
            System.out.println("IOStream Error");
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("IOStream Error");
            } catch (NullPointerException e){

            }
        }
    }


    @Override
    public List<Message> getHistory(int n) {
        if (session.isUserAuthentificated())
            path = session.getSessionUser().getName();
        else {
            System.out.println("You not log in");
            return null;
        }

        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            lines = new ArrayList<Message>();
            while ((line = reader.readLine()) != null) {
                lines.add(new Message(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("History is empty");
        } catch (IOException e) {
            System.out.println("IOStream Error");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("IOStream Error");
            } catch (NullPointerException e) {}
        }

        List<Message> nLines = new ArrayList<Message>();
        if (n >= 0) {
            if (lines.size() > n) {
                for (int i = lines.size() - n; i < lines.size(); i++) {
                    nLines.add(lines.get(i));
                }
                return nLines;
            } else {
                return lines;
            }
        } else {
            return null;
        }
    }
}
