package com.example.lenovo.zyy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lenovo on 2017/4/27.
 */

public class SPUtil {
    private  static final String SP_NAME="user";
    private  static final String USER_NAME_KEY="lastname";
    private  static final String CHAT_DEFF="chatdeff";
    private  static SharedPreferences sp;

    public static String getChatDeff(Context context){
        getSP (context);

        return sp.getString(CHAT_DEFF,"");
    }
    public static void setChatDeff(Context context,String json){
        getSP (context);
        sp.edit ().putString (CHAT_DEFF,json).apply ();
    }
    private static void getSP(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }
    public static void setLoginUser(Context context,String userName){
        getSP(context);
            sp.edit()
            .putString(USER_NAME_KEY,userName)
            .apply();

     }

    public static String getLastName(Context context){
        getSP(context);

        return sp.getString(USER_NAME_KEY,"");
    }
}
