package ru.mail.track.Messeges;

/**
 * Created by zanexess on 17.11.15.
 */
public enum Request {

    REQUEST_EXIT("\\exit"),
    REQUEST_LOGIN("\\login"),
    REQUEST_REGISTER("\\register"),
    REQUEST_INFO("\\info"),
    REQUEST_HELP("\\help"),
    REQUEST_USER("\\user"),
    REQUEST_USERPASS("\\user_pass"),
    REQUEST_CHATLIST("\\chat_list"),
    REQUEST_CHATCREATE("\\chat_create"),
    REQUEST_CHATSEND("\\chat_send"),
    REQUEST_CHATHISTORY("\\chat_history"),
    REQUEST_CHATFIND("\\chat_find"),
    REQUEST_DEFAULT("");

    private String typeValue;

    private Request(String type) {
        typeValue = type;
    }

    static public Request getType(String pType) {
        for (Request type: Request.values()) {
            if (type.getTypeValue().equals(pType)) {
                return type;
            }
        }
        return REQUEST_DEFAULT;
    }

    public String getTypeValue() {
        return typeValue;
    }
}
