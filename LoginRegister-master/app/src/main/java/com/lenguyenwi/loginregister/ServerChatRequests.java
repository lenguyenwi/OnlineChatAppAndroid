package com.lenguyenwi.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by LeNguyenwi on 10/24/2015.
 */
public class ServerChatRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://s587842793.online.de/";


    public ServerChatRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }




    public void storeUserChatDataInBackground(ChatUserContext chatUserContext, GetChatCallback chatCallback) {
        progressDialog.show();
        new StoreUserChatContextDataAsyncTask(chatUserContext, chatCallback).execute();

    }
    public void storeUserChatDataWithDateInBackground(ChatUserContext chatUserContext, GetChatCallback chatCallback) {
        progressDialog.show();
        new StoreUserChatContextWithDateDataAsyncTask(chatUserContext, chatCallback).execute();

    }
    public void storeUserChatDataWithDateAndPartnerUsernameInBackground(ChatUserContext chatUserContext, GetChatCallback chatCallback) {
        progressDialog.show();
        new StoreUserChatContextWithDateAndPartnerUsernameDataAsyncTask(chatUserContext, chatCallback).execute();

    }
    public void fetchUserChatDataInBackground(ChatUserContext chatUserContext,GetChatCallback chatCallback){
        progressDialog.show();
        new FetchUserChatContextDataAsyncTask(chatUserContext,chatCallback).execute();

    }
    public void fetchUserChatWithDateDataInBackground(ChatUserContext chatUserContext,GetChatCallback chatCallback){

        new FetchUserChatContextWithDateDataAsyncTask(chatUserContext,chatCallback).execute();

    }


    public class StoreUserChatContextDataAsyncTask extends AsyncTask<Void, Void, Void> {//1void send nothing, 2void how to want to receive the asyntask, 3void to asyntask to return
        ChatUserContext chatUserContext;
        GetChatCallback chatCallback;

        public StoreUserChatContextDataAsyncTask(ChatUserContext chatUserContext, GetChatCallback chatCallback) {
            this.chatUserContext = chatUserContext;
            this.chatCallback = chatCallback;

        }


        @Override
        protected Void doInBackground(Void... params) {
            //to access the server
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();//content all Data to send to the server
            dataToSend.add(new BasicNameValuePair("username",chatUserContext.username));
            dataToSend.add(new BasicNameValuePair("chatcontext",chatUserContext.chatContext));
            //dataToSend.add(new BasicNameValuePair("chattime",chatUserContext.chattime));
            //set the attribute for the HTTP
            HttpParams httpRequestPagrams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestPagrams,CONNECTION_TIMEOUT);//set the timeout
            HttpConnectionParams.setSoTimeout(httpRequestPagrams, CONNECTION_TIMEOUT);

            //save the clien&thepost
            HttpClient client = new DefaultHttpClient(httpRequestPagrams);
            HttpPost post = new HttpPost(SERVER_ADDRESS+"ChatData.php");//going to host the Data what we are going  to host the Server
            //post the Data we send to it
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//encode the DataTosen and give it to send
                client.execute(post);
            }catch (Exception e){
                e.printStackTrace();//can known ecxacly what wrong
                //15:19
            }

            return null;
        }

        @Override//this is what happen if the asytask is finished
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            chatCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }
    public class StoreUserChatContextWithDateDataAsyncTask extends AsyncTask<Void, Void, Void> {//1void send nothing, 2void how to want to receive the asyntask, 3void to asyntask to return
        ChatUserContext chatUserContext;
        GetChatCallback chatCallback;

        public StoreUserChatContextWithDateDataAsyncTask(ChatUserContext chatUserContext, GetChatCallback chatCallback) {
            this.chatUserContext = chatUserContext;
            this.chatCallback = chatCallback;

        }


        @Override
        protected Void doInBackground(Void... params) {
            //to access the server
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();//content all Data to send to the server
            dataToSend.add(new BasicNameValuePair("username",chatUserContext.username));
            dataToSend.add(new BasicNameValuePair("chatcontext", chatUserContext.chatContext));
            dataToSend.add(new BasicNameValuePair("chattime", chatUserContext.chattime));
            //set the attribute for the HTTP
            HttpParams httpRequestPagrams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestPagrams,CONNECTION_TIMEOUT);//set the timeout
            HttpConnectionParams.setSoTimeout(httpRequestPagrams, CONNECTION_TIMEOUT);

            //save the clien&thepost
            HttpClient client = new DefaultHttpClient(httpRequestPagrams);
            HttpPost post = new HttpPost(SERVER_ADDRESS+"ChatWithDateData.php");//going to host the Data what we are going  to host the Server
            //post the Data we send to it
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//encode the DataTosen and give it to send
                client.execute(post);
            }catch (Exception e){
                e.printStackTrace();//can known ecxacly what wrong
                //15:19
            }

            return null;
        }

        @Override//this is what happen if the asytask is finished
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            chatCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class FetchUserChatContextDataAsyncTask extends AsyncTask<Void, Void, ChatUserContext> {//1void send nothing, 2void how to want to receive the asyntask, 3void to asyntask to return
        ChatUserContext chatUserContext;
        GetChatCallback chatCallback;

        public FetchUserChatContextDataAsyncTask(ChatUserContext chatUserContext, GetChatCallback chatCallback) {
            this.chatUserContext = chatUserContext;
            this.chatCallback = chatCallback;

        }

        @Override
        protected ChatUserContext doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();//content all Data to send to the server
            dataToSend.add(new BasicNameValuePair("username", chatUserContext.username));

            //set the attribute for the HTTP&request
            HttpParams httpRequestPagrams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestPagrams,CONNECTION_TIMEOUT);//set the timeout
            HttpConnectionParams.setSoTimeout(httpRequestPagrams, CONNECTION_TIMEOUT);
            //save the clien&thepost
            HttpClient client = new DefaultHttpClient(httpRequestPagrams);
            HttpPost post = new HttpPost(SERVER_ADDRESS+"FetchChatData.php");//going to host the Data what we are going  to host the Server
            //post the Data we send to it
            //excecute the post,thuc hien lenh
            //this time when execute the post we will receive the response(lenh tra ve)

            ChatUserContext returnedChatUserContext=null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//encode the DataTosen and give it to send
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.length() == 0){
                    returnedChatUserContext = null;
                }else {

                    String userChatname = jsonObject.getString("username");
                    String chatcontext = jsonObject.getString("chatcontext");
                    returnedChatUserContext = new ChatUserContext(userChatname,chatcontext);

                }
            }catch (Exception e){
                e.printStackTrace();//can known ecxacly what wrong
                //15:19
            }


            return returnedChatUserContext;
        }
        @Override//this is what happen if the asytask is finished
        protected void onPostExecute(ChatUserContext returnedChatUserContext) {//return a ChatuserContext
            progressDialog.dismiss();
            chatCallback.done(returnedChatUserContext);
            super.onPostExecute(returnedChatUserContext);
        }
    }


    public class FetchUserChatContextWithDateDataAsyncTask extends AsyncTask<Void, Void, ChatUserContext> {//1void send nothing, 2void how to want to receive the asyntask, 3void to asyntask to return
        ChatUserContext chatUserContext;
        GetChatCallback chatCallback;

        public FetchUserChatContextWithDateDataAsyncTask(ChatUserContext chatUserContext, GetChatCallback chatCallback) {
            this.chatUserContext = chatUserContext;
            this.chatCallback = chatCallback;

        }

        @Override
        protected ChatUserContext doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();//content all Data to send to the server
            dataToSend.add(new BasicNameValuePair("username",chatUserContext.username));

            //set the attribute for the HTTP&request
            HttpParams httpRequestPagrams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestPagrams,CONNECTION_TIMEOUT);//set the timeout
            HttpConnectionParams.setSoTimeout(httpRequestPagrams, CONNECTION_TIMEOUT);
            //save the clien&thepost
            HttpClient client = new DefaultHttpClient(httpRequestPagrams);
            HttpPost post = new HttpPost(SERVER_ADDRESS+"FetchChatWithDateData.php");//going to host the Data what we are going  to host the Server
            //post the Data we send to it
            //excecute the post,thuc hien lenh
            //this time when execute the post we will receive the response(lenh tra ve)

            ChatUserContext returnedChatUserContext=null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//encode the DataTosen and give it to send
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.length() == 0){
                    returnedChatUserContext = null;
                }else {

                    String userChatname = jsonObject.getString("username");
                    String chatcontext = jsonObject.getString("chatcontext");
                    String chattime = jsonObject.getString("chattime");
                    returnedChatUserContext = new ChatUserContext(userChatname,chatcontext,chattime);

                }
            }catch (Exception e){
                e.printStackTrace();//can known ecxacly what wrong
                //15:19
            }


            return returnedChatUserContext;
        }
        @Override//this is what happen if the asytask is finished
        protected void onPostExecute(ChatUserContext returnedChatUserContext) {//return a ChatuserContext
            progressDialog.dismiss();
            chatCallback.done(returnedChatUserContext);
            super.onPostExecute(returnedChatUserContext);
        }
    }

    public void doSomethingRepeatedly( final ChatUserContext chatUserContext, final GetChatCallback chatCallback) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            public void run() {

                try{

                    new FetchUserChatContextWithDateDataAsyncTask(chatUserContext, chatCallback).execute();

                }
                catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }, 0, 1000);
    }

    public class StoreUserChatContextWithDateAndPartnerUsernameDataAsyncTask extends AsyncTask<Void, Void, Void> {//1void send nothing, 2void how to want to receive the asyntask, 3void to asyntask to return
        ChatUserContext chatUserContext;
        GetChatCallback chatCallback;

        public StoreUserChatContextWithDateAndPartnerUsernameDataAsyncTask(ChatUserContext chatUserContext, GetChatCallback chatCallback) {
            this.chatUserContext = chatUserContext;
            this.chatCallback = chatCallback;

        }


        @Override
        protected Void doInBackground(Void... params) {
            //to access the server
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();//content all Data to send to the server
            dataToSend.add(new BasicNameValuePair("username",chatUserContext.username));
            dataToSend.add(new BasicNameValuePair("chatcontext", chatUserContext.chatContext));
            dataToSend.add(new BasicNameValuePair("chattime", chatUserContext.chattime));
            dataToSend.add(new BasicNameValuePair("partner_username", chatUserContext.partner_username));
            //set the attribute for the HTTP
            HttpParams httpRequestPagrams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestPagrams,CONNECTION_TIMEOUT);//set the timeout
            HttpConnectionParams.setSoTimeout(httpRequestPagrams, CONNECTION_TIMEOUT);

            //save the clien&thepost
            HttpClient client = new DefaultHttpClient(httpRequestPagrams);
            HttpPost post = new HttpPost(SERVER_ADDRESS+"ChatWithDateAndPartnerUserNameData.php");//going to host the Data what we are going  to host the Server
            //post the Data we send to it
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//encode the DataTosen and give it to send
                client.execute(post);
            }catch (Exception e){
                e.printStackTrace();//can known ecxacly what wrong
                //15:19
            }

            return null;
        }

        @Override//this is what happen if the asytask is finished
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            chatCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }
    public class FetchUserChatContextWithDateAndPartnerUsernameDataAsyncTask extends AsyncTask<Void, Void, ChatUserContext> {//1void send nothing, 2void how to want to receive the asyntask, 3void to asyntask to return
        ChatUserContext chatUserContext;
        GetChatCallback chatCallback;

        public FetchUserChatContextWithDateAndPartnerUsernameDataAsyncTask(ChatUserContext chatUserContext, GetChatCallback chatCallback) {
            this.chatUserContext = chatUserContext;
            this.chatCallback = chatCallback;

        }

        @Override
        protected ChatUserContext doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();//content all Data to send to the server
            dataToSend.add(new BasicNameValuePair("username",chatUserContext.username));
            dataToSend.add(new BasicNameValuePair("partner_username",chatUserContext.partner_username));
            //set the attribute for the HTTP&request
            HttpParams httpRequestPagrams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestPagrams,CONNECTION_TIMEOUT);//set the timeout
            HttpConnectionParams.setSoTimeout(httpRequestPagrams, CONNECTION_TIMEOUT);
            //save the clien&thepost
            HttpClient client = new DefaultHttpClient(httpRequestPagrams);
            HttpPost post = new HttpPost(SERVER_ADDRESS+"FetchChatWithDateAndPartnerUsernameData.php");//going to host the Data what we are going  to host the Server
            //post the Data we send to it
            //excecute the post,thuc hien lenh
            //this time when execute the post we will receive the response(lenh tra ve)

            ChatUserContext returnedChatUserContext=null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//encode the DataTosen and give it to send
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.length() == 0){
                    returnedChatUserContext = null;
                }else {

                    String userChatname = jsonObject.getString("username");
                    String chatcontext = jsonObject.getString("chatcontext");
                    String chattime = jsonObject.getString("chattime");
                    String partner_username = jsonObject.getString("partner_username");
                    returnedChatUserContext = new ChatUserContext(userChatname,chatcontext,chattime,partner_username);

                }
            }catch (Exception e){
                e.printStackTrace();//can known ecxacly what wrong
                //15:19
            }


            return returnedChatUserContext;
        }
        @Override//this is what happen if the asytask is finished
        protected void onPostExecute(ChatUserContext returnedChatUserContext) {//return a ChatuserContext
            progressDialog.dismiss();
            chatCallback.done(returnedChatUserContext);
            super.onPostExecute(returnedChatUserContext);
        }
    }
    public void doSomethingRepeatedlyWithPartnerUsername( final ChatUserContext chatUserContext, final GetChatCallback chatCallback) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            public void run() {

                try{

                    new FetchUserChatContextWithDateAndPartnerUsernameDataAsyncTask(chatUserContext, chatCallback).execute();

                }
                catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }, 0, 1000);
    }

}
