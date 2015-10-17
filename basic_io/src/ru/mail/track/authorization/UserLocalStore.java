package ru.mail.track.authorization;

import ru.mail.track.session.User;

import java.util.ArrayList;

/**
 * Локальное хранилище пользователей
 */
public class UserLocalStore implements UserStore {
    private ArrayList<User> users = new ArrayList<User>();

    @Override
    public boolean isUserExist(String name) {
        for (User user : users) {
            if (user != null && user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public User getUser(String name, String pass) {
        for (User user : users) {
            if (user != null && user.getName().equals(name) && user.getPass().equals(pass)) {
                return user;
            }
        }
        return null;
    }
}
