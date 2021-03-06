package ru.mail.track.Stores;

import ru.mail.track.Data.User;

public interface  UserStore {
    // Проверка на существования User с именем name
    boolean isUserExist(String name);

    // Проверка на существование User с id=id
    boolean isUserExist(Long id);

    // Добавить пользователя
    void addUser(User user);

    // Получить пользователя по имени и паролю
    User getUser(String name, String pass);

    // Получить пользователя по id
    User getUser(Long id);

    // Обновить информацию о пользователе user
    void update(User user);
}
