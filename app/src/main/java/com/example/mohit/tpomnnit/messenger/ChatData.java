package com.example.mohit.tpomnnit.messenger;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mohit on 12/10/17.
 */

public class ChatData {
    String otheruser;
    ArrayList<String> chat;
    ArrayList<Integer> flag;
    public ChatData(){

    }

    public String getOtheruser() {
        return otheruser;
    }

    public void setOtheruser(String otheruser) {
        this.otheruser = otheruser;
    }

    public ArrayList<String> getChat() {
        return chat;
    }

    public void setChat(ArrayList<String> chat) {
        this.chat = chat;
    }

    public ArrayList<Integer> getFlag() {
        return flag;
    }

    public void setFlag(ArrayList<Integer> flag) {
        this.flag = flag;
    }
}
