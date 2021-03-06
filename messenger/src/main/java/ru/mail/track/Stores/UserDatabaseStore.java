package ru.mail.track.Stores;

import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.Dao.GenericDao;
import ru.mail.track.Data.User;

import java.sql.Connection;
import java.util.List;

public class UserDatabaseStore implements UserStore{
    private GenericDao userDao;

    public UserDatabaseStore(DaoFactory daoFactory) {
        try {
            userDao = daoFactory.getDao(daoFactory.getContext(), User.class);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isUserExist(String name) {
        List<User> list;
        try {
            list = userDao.getAll();
            for (User user : list) {
                if (user.getName().equals(name)){
                    return true;
                }
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        try {
            userDao.persist(user);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(String name, String pass) {
        List<User> list;
        try {
            list = userDao.getAll();
            for (User user : list) {
                if (user.getName().equals(name) && user.getPassword().equals(pass)){
                    return user;
                }
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(Long id) {
        try {
            User user = (User)userDao.getByPK(id);
            return user;
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(User user) {
        try {
            userDao.update(user);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isUserExist(Long id) {
        List<User> list;
        try {
            list = userDao.getAll();
            for (User user : list) {
                if (user.getId() == id){
                    return true;
                }
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return false;
    }
}
