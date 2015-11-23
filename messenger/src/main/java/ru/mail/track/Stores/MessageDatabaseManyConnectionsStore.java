package ru.mail.track.Stores;

import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.Dao.GenericDao;
import ru.mail.track.Data.Chat;
import ru.mail.track.Data.Message;
import ru.mail.track.Data.User;
import ru.mail.track.Data.UserChatRelation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDatabaseManyConnectionsStore implements MessageStore {

    private DaoFactory daoFactory;

    public MessageDatabaseManyConnectionsStore(DaoFactory daoFactory){
            this.daoFactory = daoFactory;
    }

    @Override
    public List<Long> getParticipantByChatId(Long chatId) {
        List<Long> participant = new ArrayList<>();
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao userChatDao = daoFactory.getDao(connection, UserChatRelation.class);
            List<UserChatRelation> userChatRelations = userChatDao.getAll();
            for (UserChatRelation userChatRelation : userChatRelations){
                if (userChatRelation.getChatId() == chatId){
                    participant.add(userChatRelation.getUserId());
                }
            }
            connection.close();
        } catch (PersistException | SQLException e){
            e.printStackTrace();
        }
        return participant;
    }

    @Override
    public Chat getChatById(Long chatId) {
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao chatDao = daoFactory.getDao(connection, Chat.class);
            Chat chat = (Chat)chatDao.getByPK(chatId);
            connection.close();
            return chat;
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        List<Long> messages = new ArrayList<>();
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao messageDao = daoFactory.getDao(connection, Message.class);
            List<Message> allMessages = messageDao.getAll();
            for (Message message : allMessages) {
                if (message.getChatId() == chatId){
                    messages.add(message.getId());
                }
            }
            connection.close();
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message getMessageById(Long messageId) {
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao messageDao = daoFactory.getDao(connection, Message.class);
            Message message = (Message)messageDao.getByPK(messageId);
            connection.close();
            return message;
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) {
        List<Long> chats = new ArrayList<>();
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao userChatDao = daoFactory.getDao(connection, UserChatRelation.class);
            List<UserChatRelation> userChatRelations = userChatDao.getAll();
            for (UserChatRelation userChatRelation : userChatRelations){
                if (userChatRelation.getUserId() == userId){
                    chats.add(userChatRelation.getChatId());
                }
            }
            connection.close();
        } catch (PersistException | SQLException e){
            e.printStackTrace();
        }
        return chats;
    }

    @Override
    public void addChat(Chat chat) {
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao userChatDao = daoFactory.getDao(connection, UserChatRelation.class);
            GenericDao chatDao = daoFactory.getDao(connection, Chat.class);
            Chat r = (Chat)chatDao.persist(chat);
            Long t = r.getId();
            List<Long> usersIds = chat.getParticipantIds();
            for (Long aLong : usersIds) {
                UserChatRelation userChatRelation = new UserChatRelation(t, aLong);
                userChatDao.persist(userChatRelation);
            }
            connection.close();
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMessage(Message msg) {
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao messageDao = daoFactory.getDao(connection, Message.class);
            messageDao.persist(msg);
            connection.close();
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
    }
}
