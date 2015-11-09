package ru.mail.track.Messeges;

/**
 * Created by zanexess on 09.11.15.
 */
public enum MessageType{
    //Команды
    MSG_LOGIN,        //+
    MSG_REGISTER,     //+
    MSG_SEND,
    MSG_HELP,         //+
    MSG_FIND,
    MSG_HISTORY,
    MSG_INFO,         //+
    MSG_USER,         //+
    MSG_USERPASS,     //+
    MSG_LOGOUT,       //+
    MSG_ERROR,        //+
    MSG_CONFIGURE,    //+

    //Ответы сервера
    SRV_SUCCESS,      //+
    SRV_ERROR,        //+
    SRV_INVALIDINPUT, //+
    SRV_LOGINERROR,   //+
    SRV_LOGINSUCCESS, //+
}