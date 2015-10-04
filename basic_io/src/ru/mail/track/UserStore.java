package ru.mail.track;

import java.util.ArrayList;

public class UserStore {
    private ArrayList<User> users = new ArrayList<User>();

    // Вам нужно выбрать, как вы будете хранить ваших пользователей, например в массиве User users[] = new User[100];

    // проверить, есть ли пользователь с таким именем
    // если есть, вернуть true
    boolean isUserExist(String name) {
        for(User o: users){
            if (o != null && o.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }

    // Добавить пользователя в хранилище
    void addUser(User user) {
        users.add(user);
    }

    // Получить пользователя по имени и паролю
    User getUser(String name, String pass) {
        for (User o : users)
            if (o != null && o.getName().equals(name) && o.getPass().equals(pass)) {
                return o;
            }
        return null;
    }
}
