package com.lenguyenwi.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Chat extends ActionBarActivity  implements View.OnClickListener {
    Button bSend, bTakeMessage, bChatLogout;
    EditText etEnteredMessage, etUsernameChat, etchatcontext, etUsername;
    ChatUserLocalStore chatUserLocalStore; //11:55
    UserLocalStore userLocalStore;
    PartnerUserLocalStore partnerUserLocalStore;
    volatile boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        etEnteredMessage = (EditText) findViewById(R.id.etEnteredMessage);
        etUsernameChat = (EditText) findViewById(R.id.etUsernameChat);
        etUsername = (EditText) findViewById(R.id.etUsername);
        bSend = (Button) findViewById(R.id.bSend);
        bSend.setOnClickListener(this);


        bChatLogout = (Button) findViewById(R.id.bChatLogout);
        bChatLogout.setOnClickListener(this);

        etchatcontext = (EditText) findViewById(R.id.etchatcontext);
        chatUserLocalStore = new ChatUserLocalStore(this);
        userLocalStore = new UserLocalStore(this);
        partnerUserLocalStore = new PartnerUserLocalStore(this);
        //

        //------------------
        //------------------

    }//end of OnCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSend:
               sendMessage();
                break;
            case R.id.bChatLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent loginIntent = new Intent(this, Login.class);
                startActivity(loginIntent);
                break;

        }
    }

    private void postMessageChatUserContextWithdate(ChatUserContext chatUserContext) {
        ServerChatRequests serverChatRequests = new ServerChatRequests(this);
        serverChatRequests.storeUserChatDataWithDateInBackground(chatUserContext, new GetChatCallback() {
            @Override
            public void done(ChatUserContext chatUserContext) {

            }
        });
    }
    private void postMessageChatUserContextWithdateAndPartnerUsername(ChatUserContext chatUserContext) {
        ServerChatRequests serverChatRequests = new ServerChatRequests(this);
        serverChatRequests.storeUserChatDataWithDateAndPartnerUsernameInBackground(chatUserContext, new GetChatCallback() {
            @Override
            public void done(ChatUserContext chatUserContext) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true) {
            displayUserDetails();
        }
        displayParterUserDetails();
        //Bigbang first Value of Database
        sendMessageAtStart();
        //takeMessageAutomatic();
        takeMessageAutomaticWithPartnerUser();
    }

    private boolean authenticate() {
        if (userLocalStore.getLoggedInUser() == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        etUsername.setText(user.username);

    }
    private void displayParterUserDetails() {
        User partnerUser = partnerUserLocalStore.getPartnernUser();
        etUsernameChat.setText(partnerUser.username);

    }

    private void postMessageChatUserContext(ChatUserContext chatUserContext) {
        ServerChatRequests serverChatRequests = new ServerChatRequests(this);
        //give to ChatuserContext which data to store
        serverChatRequests.storeUserChatDataInBackground(chatUserContext, new GetChatCallback() {
            @Override
            public void done(ChatUserContext chatUserContext) {
                //TODO
            }
        });
    }



    public void takeMessage() {
        String usernameChat = etUsername.getText().toString();
        ChatUserContext chatUserContext2 = new ChatUserContext(usernameChat);//13:30
        ServerChatRequests serverChatRequests = new ServerChatRequests(this);
        //serverChatRequests.fetchUserChatDataInBackground(chatUserContext2, new GetChatCallback() {
        serverChatRequests.fetchUserChatWithDateDataInBackground(chatUserContext2, new GetChatCallback() {
            @Override
            public void done(ChatUserContext returnedChatUserContext) {
                //chatUserLocalStore.putChatedContextToList(returnedChatUserContext);//1,//2
                // chatUserLocalStore.putChatedContextToList(new ChatUserContext("1","00"));
                //String chatContext = chatUserLocalStore.getAllChatcontext();//1
                //etchatcontext.setText(chatContext);//1

                //List<ChatUserContext>allchatList = chatUserLocalStore.getAllChatsList();//2
                //String chatCOntextinAllChatList = chatUserLocalStore.getStringAllchatlist(allchatList);//2
                //etchatcontext.setText(chatCOntextinAllChatList);//2
                String returnedChat = chatUserLocalStore.allChatcontextToString(returnedChatUserContext);
                chatUserLocalStore.addreturnedchatusercontextIntoList(returnedChat);
                String chatContext = chatUserLocalStore.getStringofAllContextFromStringList();
                etchatcontext.setText(chatContext);
                //etchatcontext.setText(returnedChatUserContext.chatContext);
            }
        });

    }
    public void takeMessageAutomatic() {
        String usernameChat = etUsernameChat.getText().toString();
        ChatUserContext chatUserContext2 = new ChatUserContext(usernameChat);//13:30
        ServerChatRequests serverChatRequests = new ServerChatRequests(this);
        serverChatRequests.doSomethingRepeatedly(chatUserContext2, new GetChatCallback() {
            @Override
            public void done(ChatUserContext returnedChatUserContext) {
                String returnedChat = chatUserLocalStore.allChatcontextToString(returnedChatUserContext);
                chatUserLocalStore.addreturnedchatusercontextIntoList(returnedChat);
                String chatContext = chatUserLocalStore.getStringofAllContextFromStringList();
                etchatcontext.setText(chatContext);
                //etchatcontext.setText(returnedChatUserContext.chatContext);
            }
        });
    }

    public void takeMessageAutomaticWithPartnerUser() {
        String username = etUsername.getText().toString();
        String partner_username = etUsernameChat.getText().toString();
        ChatUserContext chatUserContextOfPartner = new ChatUserContext(partner_username,username);//13:30
        ServerChatRequests serverChatRequests = new ServerChatRequests(this);
        //serverChatRequests.doSomethingRepeatedly(chatUserContext2, new GetChatCallback() {
        serverChatRequests.doSomethingRepeatedlyWithPartnerUsername(chatUserContextOfPartner, new GetChatCallback() {
            @Override
            public void done(ChatUserContext returnedChatUserContext) {
                String returnedChat = chatUserLocalStore.allChatcontextToString(returnedChatUserContext);
                chatUserLocalStore.addreturnedchatusercontextIntoList(returnedChat);
                String chatContext = chatUserLocalStore.getStringofAllContextFromStringList();
                etchatcontext.setText(chatContext);
                //etchatcontext.setText(returnedChatUserContext.chatContext);
            }
        });
    }
    public void sendMessage(){
        //nhap messa.. chat vao trong online Database tuong duong voi button register
        String enteredMessage = etEnteredMessage.getText().toString();
        String username = etUsername.getText().toString();//chua co userName trong Framework!!!
        String partner_username = etUsernameChat.getText().toString();
        //get formate Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        ChatUserContext chatUserContext = new ChatUserContext(username, enteredMessage, currentDateandTime,partner_username);
        postMessageChatUserContextWithdateAndPartnerUsername(chatUserContext);
        String myChat = chatUserLocalStore.allChatcontextToString(chatUserContext);
        chatUserLocalStore.addreturnedchatusercontextIntoList(myChat);


    }
    public void sendMessageAtStart(){
        //nhap messa.. chat vao trong online Database tuong duong voi button register
        String username = etUsername.getText().toString();//chua co userName trong Framework!!!
        String partner_username = etUsernameChat.getText().toString();
        //get formate Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        ChatUserContext chatUserContext = new ChatUserContext(partner_username, "Welcome To VChatAPP", currentDateandTime,username);
        postMessageChatUserContextWithdateAndPartnerUsername(chatUserContext);



    }
}
