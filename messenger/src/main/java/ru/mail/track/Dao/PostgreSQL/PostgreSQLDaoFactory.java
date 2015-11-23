package ru.mail.track.Dao.PostgreSQL;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.Dao.GenericDao;
import ru.mail.track.Data.Chat;
import ru.mail.track.Data.Message;
import ru.mail.track.Data.User;
import ru.mail.track.Data.UserChatRelation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import java.sql.*;
import org.apache.commons.dbcp2.*;




public class PostgreSQLDaoFactory implements DaoFactory<Connection> {

    private final String user = "senthil";
    private final String password = "ubuntu";
    private final String url = "jdbc:postgresql://178.62.140.149:5432/Zanexess";
    private final String driver = "org.postgresql.Driver";
    static Logger log = LoggerFactory.getLogger(PostgreSQLDaoFactory.class);
    private BasicDataSource connectionPool;

    private Map<Class, DaoCreator> creators;

    public Connection getContext() throws PersistException {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(url, user, password);
//        } catch (SQLException e) {
//            throw new PersistException(e);
//        }
//        return  connection;

//        // Create the ConnectionPool:
//        JDBCConnectionPool pool = new JDBCConnectionPool(driver, url, user, password);
//        // Get a connection:
//        Connection con = pool.checkOut();
//        // Return the connection:
//        pool.checkIn(con);
//        System.out.println(con.toString());
//        return con;
        //TODO Connection Pool


        try {

            Connection connection = connectionPool.getConnection();
            log.info(connection.toString());
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        //TODO Comment
        //log.info(connection.toString() + " " + dtoClass.toString());
        return creator.create(connection);
    }

    public PostgreSQLDaoFactory() {
        connectionPool = new BasicDataSource();
        connectionPool.setUsername(user);
        connectionPool.setPassword(password);
        connectionPool.setDriverClassName(driver);
        connectionPool.setUrl(url);
        connectionPool.setInitialSize(10);
        try {
            Connection connection = connectionPool.getConnection();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Class.forName(driver);//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<>();
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new PostgreSQLUserDao(connection);
            }
        });
        creators.put(Message.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new PostgreSQLMessageDao(connection);
            }
        });
        creators.put(Chat.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new PostgreSQLChatDao(connection);
            }
        });
        creators.put(UserChatRelation.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new PostgreSQLUserChatDao(connection);
            }
        });
    }
}
