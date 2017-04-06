package com.lenguyenwi.loginregister;

import java.util.List;

/**
 * Created by LeNguyenwi on 10/26/2015.
 */

interface GetChatListCallback {
    public abstract void done(List<ChatUserContext> chatUserContextList);
}