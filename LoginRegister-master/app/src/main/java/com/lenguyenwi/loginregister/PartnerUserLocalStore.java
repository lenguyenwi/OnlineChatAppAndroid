package com.lenguyenwi.loginregister;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LeNguyenwi on 10/29/2015.
 */
public class PartnerUserLocalStore {
    public static final String SP_NAME = "PartnerUserDetails";

    SharedPreferences userLocalDatabase;

    public PartnerUserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }
    public void storeChatPartnerUserData(User user) {
        SharedPreferences.Editor partnerUserLocalDatabaseEditor = userLocalDatabase.edit();

        partnerUserLocalDatabaseEditor.putString("username", user.username);

        partnerUserLocalDatabaseEditor.commit();
    }
    public User getPartnernUser() {

        String name = userLocalDatabase.getString("username", "");


        User user = new User(name);
        return user;
    }
}
