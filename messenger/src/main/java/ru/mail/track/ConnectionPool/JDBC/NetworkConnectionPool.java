package ru.mail.track.ConnectionPool.JDBC;

/**
 * Created by zanexess on 22.11.15.
 */
public class NetworkConnectionPool extends ObjectPool {
    @Override
    protected Object create() {
        return null;
    }

    @Override
    public boolean validate(Object o) {
        return false;
    }

    @Override
    public void expire(Object o) {

    }
}
