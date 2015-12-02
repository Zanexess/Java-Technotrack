package ru.mail.track.Comands;

import ru.mail.track.Data.Chat;
import ru.mail.track.Session.Session;
import ru.mail.track.Stores.MessageStore;
import ru.mail.track.Stores.UserStore;

public class ChatCreateCommand implements Command {
    private MessageStore messageStore;
    private UserStore userStore;

    public ChatCreateCommand(MessageStore messageStore, UserStore userStore) {
        this.messageStore = messageStore;
        this.userStore = userStore;
    }

    public Result execute(Session session, String[] args) {
        if (session.isUserAuthentificated()) {
            if (args.length == 1) {
                return new Result(Result.Status.InvalidInput,
                        "Format of command: \\chat_create <Participant id list>");
            } else if (args.length == 2) {
                Long id = Long.parseLong(args[1]);
                Long aLong = session.getSessionUser().getId();
                String[] strings = new String[2];
                strings[0] = "The chat already exists";
                strings[1] = id.toString();
                if (messageStore.getChatsByUserId(aLong).contains(id)) ;
                return new Result(Result.Status.Success, strings);
            } else {

                // Проверка на существавание всех пользователей в <list>
                boolean usersExists = true;
                // Добавил ли пользователь себя
                boolean inChat = false;
                Long aLong = session.getSessionUser().getId();

                if (userStore.getUser(aLong) != null) {
                    for (int i = 1; i < args.length; i++) {
                        try {
                            if (!userStore.isUserExist(Long.parseLong(args[i]))) {
                                usersExists = false;
                            }
                            if (Long.parseLong(args[i]) == aLong) {
                                inChat = true;
                            }
                        } catch (NumberFormatException e) {
                            return new Result(Result.Status.InvalidInput, "Parsing Wrong");
                        }
                    }
                    if (inChat) {
                        if (usersExists) {
                            Chat chat = new Chat();
                            for (int i = 1; i < args.length; i++) {
                                try {
                                    chat.addParticipant(Long.parseLong(args[i]));
                                } catch (NumberFormatException e) {
                                    return new Result(Result.Status.InvalidInput, "Parsing Wrong");
                                }
                            }
                            messageStore.addChat(chat);
                            return new Result(Result.Status.Success, "Chat successfully created!");
                        } else {
                            return new Result(Result.Status.Error, "User in list doesn't exist");
                        }
                    } else {
                        return new Result(Result.Status.Error, "You didn't add yourself in chat");
                    }
                }
            }
        }
        return new Result(Result.Status.LoginError, "You are not log in");
    }
}