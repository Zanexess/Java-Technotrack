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
    private List<String> lines;
    private File file;
    private FileWriter writer = null;
    private BufferedReader reader = null;
    private String path;

    public DataFileStorage(Session session) {
        this.session = session;
    }

    @Override
    public void addMessage(String str) {
        try {
            path = session.getSessionUser().getName();
            writer = new FileWriter(path, true);
            writer.write(str);
            writer.close();
        } catch (FileNotFoundException e) {
            file = new File(path);
            addMessage(str);
        } catch (IOException e) {
            System.out.println("IOStream Error");
        }

    }


    @Override
    public List<String> getHistory(int n) {
        try {
            path = session.getSessionUser().getName();
            reader = new BufferedReader(new FileReader(path));
            String line;
            lines = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("History is empty");
        } catch (IOException e) {
            System.out.println("IOStream Error");
        } catch (NullPointerException e) {
            System.out.println("You not log in");
        }

        List<String> nLines = new ArrayList<String>();
        if (n >= 0) {
            if (lines.size() <= n)
            {
                return lines;
            } else {
                for (int i = lines.size() - n; i < lines.size(); i++) {
                    nLines.add(lines.get(i));
                }
                return nLines;
            }
        } else {
            return lines;
        }
    }

    @Override
    public void printHistory(List<String> history) {

        if (history != null)
            for (String str: history) {
                System.out.println(str);
            }
        history = null;
    }
}
