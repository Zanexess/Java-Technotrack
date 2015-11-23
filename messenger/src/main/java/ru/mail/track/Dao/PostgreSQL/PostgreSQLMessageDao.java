package ru.mail.track.Dao.PostgreSQL;

import ru.mail.track.Dao.AbstractJDBCDao;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.Data.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class PostgreSQLMessageDao extends AbstractJDBCDao<Message> {
    private Connection connection;

    private class PersistMessage extends Message {
        public void setId(Long id) {
            super.setMessageId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, message, senderid, chatid, timestamp FROM public.message ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO public.message (message, senderid, chatid, timestamp) \n" +
                "VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE public.message \n" +
                "SET message = ?, senderid  = ?, chatid = ?, timestamp = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM public.message WHERE id= ?;";
    }


    public PostgreSQLMessageDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Message> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Message> result = new LinkedList<Message>();
        try {
            while (rs.next()) {
                PersistMessage message = new PersistMessage();
                message.setId(rs.getLong("id"));
                message.setMessage(rs.getString("message"));
                message.setSenderId(rs.getLong("senderid"));
                message.setChatId(rs.getLong("chatid"));
                message.setTimestamp(rs.getString("timestamp"));
                result.add(message);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Message object) throws PersistException {
        try {
            statement.setString(1, object.getMessage());
            statement.setLong(2, object.getSenderId());
            statement.setLong(3, object.getChatId());
            statement.setString(4, object.getTimestamp());
            statement.setLong(5, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Message object) throws PersistException {
        try {
            statement.setString(1, object.getMessage());
            statement.setLong(2, object.getSenderId());
            statement.setLong(3, object.getChatId());
            statement.setString(4, object.getTimestamp());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected String getSequence() {
        return "message_id_seq";
    }

    @Override
    protected String getSeqClass() {
        return "message";
    }
}
