package com.example.mohit.tpomnnit.messenger;

import java.util.ArrayList;

/**
 * Created by mohit on 12/10/17.
 */

public class Messages {

    String users;
    ChatData chatdata;

    public ChatData getChatdata() {
        return chatdata;
    }

    public void setChatdata(ChatData chatdata) {
        this.chatdata = chatdata;
    }

    public Messages(){

    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}

