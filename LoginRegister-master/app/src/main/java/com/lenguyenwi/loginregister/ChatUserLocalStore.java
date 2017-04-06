package com.lenguyenwi.loginregister;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by LeNguyenwi on 10/23/2015.
 */
public class ChatUserLocalStore {
    public static final String SP_NAME = "chatDetails";
    SharedPreferences chatLocalDatabase;
    List<ChatUserContext > allChatsList = new ArrayList<>();
    ArrayList<String> allChatUsercontextStringList = new ArrayList<>();



    public ChatUserLocalStore(Context context){
        chatLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }
    public void storeChatData(ChatUserContext chatUserContext){
        SharedPreferences.Editor spEdittor = chatLocalDatabase.edit();
        spEdittor.putString("username",chatUserContext.username);
        spEdittor.putString("chatcontext",chatUserContext.chatContext);
        spEdittor.commit();
    }
    public ChatUserContext getChatedContext(){
        String username = chatLocalDatabase.getString("username", "");
        String chatContext = chatLocalDatabase.getString("chatcontext","");

        ChatUserContext chatedContext = new ChatUserContext(username,chatContext);
        return  chatedContext;
    }

    public void setUserChatedContext(Boolean chated){
        SharedPreferences.Editor spEditor = chatLocalDatabase.edit();
        spEditor.putBoolean("Chated", chated);
        spEditor.commit();
    }
    public void clearUserChatedData(){
        SharedPreferences.Editor spEditor = chatLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
    public void putChatedContextToList(ChatUserContext returnedchatUserContext){
        allChatsList.add(returnedchatUserContext);


    }

    public String getAllChatcontext() {
        String allchatcontext="";
        for (ChatUserContext chatUserContext:allChatsList) {
            String username = chatUserContext.username;
            String chattime = chatUserContext.chattime;
            String chat = chatUserContext.chatContext;
            String completedChat = username.concat("("+chattime+")").concat(" : "+chat);

           // allchatcontext.concat(chat);
            allchatcontext=allchatcontext.concat(completedChat + "\n");
        }
        return allchatcontext;
    }
    public  String getStringAllchatlist(List<ChatUserContext> allchatlist){
        this.allChatsList=allchatlist;
        String chat = "";
        for (ChatUserContext chatusercontext:allChatsList) {
            chat += chatusercontext.getChattime().concat("\n 1");
        }
        return  chat;
    }
    public String allChatcontextToString (ChatUserContext returnedchatusercontext) {
        String stringAllchatcontext="";

            String username = returnedchatusercontext.username;
            String chattime = returnedchatusercontext.chattime;
            String chat = returnedchatusercontext.chatContext;
            String completedChat = username.concat("("+chattime+")").concat(" : "+chat);

            // stringAllchatcontext.concat(chat);
            stringAllchatcontext=stringAllchatcontext.concat(completedChat + "\n");

        return stringAllchatcontext;
    }
    public void addreturnedchatusercontextIntoList(String returnedChatuserContextString){

        if (!allChatUsercontextStringList.contains(returnedChatuserContextString)){
            allChatUsercontextStringList.add(returnedChatuserContextString);
        }

          //  if (doithaveSameStringInTHeList(returnedChatuserContextString)==false){
            //    this.allChatUsercontextStringList.add(returnedChatuserContextString);
            //}


        ;

    }
    private Boolean doithaveSameStringInTHeList(String returnedChatcontext){
        boolean compare = true;
        for (String chats:allChatUsercontextStringList) {
            if (chats.equals(returnedChatcontext)==true){
                compare= true;


            }
            else
            {compare = false;}

        }
      return compare;
    };
    public String getStringofAllContextFromStringList (){
        String allStringfromreturnedChatStringList="";
        for (String chats: this.allChatUsercontextStringList) {
            allStringfromreturnedChatStringList+=chats ;
        }

        return allStringfromreturnedChatStringList;
    }


    public List<ChatUserContext> getAllChatsList() {
        return allChatsList;
    }
}
