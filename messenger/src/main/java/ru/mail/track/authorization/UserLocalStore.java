package ru.mail.track.authorization;

import ru.mail.track.session.User;

import java.util.ArrayList;

/**
 * Локальное хранилище пользователей
 */
public class UserLocalStore implements UserStore {
    private ArrayList<User> users = new ArrayList<User>();

    public boolean isUserExist(String name) {
        for (User user : users) {
            if (user != null && user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUser(String name, String pass) {
        for (User user : users) {
            if (user != null && user.getName().equals(name) && user.getPassword().equals(pass)) {
                return user;
            }
        }
        return null;
    }

    public User getUser(Long id){
        for (User user: users) {
            if (user != null && user.getId() == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(User user) {

    }
}
