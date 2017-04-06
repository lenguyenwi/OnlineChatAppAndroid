package com.lenguyenwi.loginregister;

/**
 * Created by LeNguyenwi on 10/23/2015.
 */
public class ChatUserContext {
    String username,chatContext,chattime,partner_username;
    public ChatUserContext(String username, String partner_username){
        this(username,"","",partner_username);
    }
    public ChatUserContext(String username, String chatContext,String chattime){
        this.username = username;
        this.chatContext = chatContext;
        this.chattime=chattime;
    }
    public ChatUserContext(String username, String chatContext,String chattime,String partner_username){
        this.username = username;
        this.chatContext = chatContext;
        this.chattime=chattime;
        this.partner_username = partner_username;
    }
    public ChatUserContext(String username) {
        this(username, "");
    }





        public boolean equals(ChatUserContext other) {
            if (!(other instanceof ChatUserContext)) {
                return false;
            }

            ChatUserContext that = (ChatUserContext) other;

            // Custom equality check here.
            return true;
        }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChatContext() {
        return chatContext;
    }

    public void setChatContext(String chatContext) {
        this.chatContext = chatContext;
    }

    public String getChattime() {
        return chattime;
    }

    public void setChattime(String chattime) {
        this.chattime = chattime;
    }


}


