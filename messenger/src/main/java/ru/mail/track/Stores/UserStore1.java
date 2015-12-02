package ru.mail.track.Stores;

import ru.mail.track.Data.User;

public interface  UserStore1 {
    // Проверка на существования User с именем name
    boolean isUserExist(String name);

    // Добавить пользователя
    void addUser(User user);

    // Получить пользователя по имени и паролю
    User getUser(String name, String pass);

    // Получить пользователя по id
    User getUser(Long id);

    // Обновить информацию о пользователе user
    void update(User user);
}
