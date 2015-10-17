package ru.mail.track.authorization;

import ru.mail.track.session.Session;
import ru.mail.track.session.User;

import java.io.*;

/**
 * Набросок файлового хранилища пользователей.
 */

public class UserFileStore implements UserStore {

    private Session session;
    private File file;
    private FileWriter writer = null;
    private BufferedReader reader = null;
    private String path;

    public UserFileStore(String path) {
        this.path = path;
    }

    @Override
    public boolean isUserExist(String name) {
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(" ");
                if (temp[1] != null && temp[1].equals(name)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("History is empty");
        } catch (IOException e) {
            System.out.println("IOStream Error");
        } catch (NullPointerException e) {
            System.out.println("You not log in");
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        try {
            writer = new FileWriter(path, true);
            writer.write(user.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            file = new File(path);
        } catch (IOException e) {
            System.out.println("IOStream Error");
        }
    }

    @Override
    public User getUser(String name, String pass) {
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(" ");
                if (temp[1] != null && temp[1].equals(name) && temp[2] != null && temp[2].equals(pass)) {
                    return new User(temp[0], temp[1], temp[2]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("History is empty");
        } catch (IOException e) {
            System.out.println("IOStream Error");
        } catch (NullPointerException e) {
            System.out.println("You not log in");
        }
        return null;
    }
}