package ru.mail.track.data;

import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.Dao.GenericDao;
import ru.mail.track.session.User;
import ru.mail.track.session.UserChatRelation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MessageDatabaseStore implements MessageStore {
    private DaoFactory daoFactory;
    private GenericDao messageDao, chatDao, userDao, userChatDao;
    private Connection connection;

    public MessageDatabaseStore(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
        try {
            this.connection = (Connection) daoFactory.getContext();
            messageDao = daoFactory.getDao(connection, Message.class);
            chatDao = daoFactory.getDao(connection, Chat.class);
            userDao = daoFactory.getDao(connection, User.class);
            userChatDao = daoFactory.getDao(connection, UserChatRelation.class);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Long> getParticipantByChatId(Long chatId) {
        List<Long> participant = new ArrayList<Long>();
        try {
            List<UserChatRelation> userChatRelations = userChatDao.getAll();
            for (UserChatRelation userChatRelation : userChatRelations){
                if (userChatRelation.getChatId() == chatId){
                    participant.add(userChatRelation.getUserId());
                }
            }
        } catch (PersistException e){
            e.printStackTrace();
        }
        return participant;
    }

    @Override
    public Chat getChatById(Long chatId) {
        try {
            return (Chat)chatDao.getByPK(chatId);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        List<Long> messages = new ArrayList<Long>();
        try {
            List<Message> allMessages = messageDao.getAll();
            for (Message message : allMessages) {
                if (message.getChatId() == chatId){
                    messages.add(message.getId());
                }
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message getMessageById(Long messageId) {
        try {
            return (Message)messageDao.getByPK(messageId);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) {
        List<Long> chats = new ArrayList<Long>();
        try {
            List<UserChatRelation> userChatRelations = userChatDao.getAll();
            for (UserChatRelation userChatRelation : userChatRelations){
                if (userChatRelation.getUserId() == userId){
                    chats.add(userChatRelation.getChatId());
                }
            }
        } catch (PersistException e){
            e.printStackTrace();
        }
        return chats;
    }

    @Override
    public void addChat(Chat chat) {
        try {
            Chat r = (Chat)chatDao.persist(chat);
            Long t = r.getId();
            List<Long> usersIds = chat.getParticipantIds();
            for (Long aLong : usersIds) {
                UserChatRelation userChatRelation = new UserChatRelation(t, aLong);
                userChatDao.persist(userChatRelation);
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMessage(Message msg) {
        //TODO
        try {
            messageDao.persist(msg);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }
}
