package com.github.pavelkv96.libs.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.github.pavelkv96.libs.constants.ApiConstants;

public class Account {
    public String access_token;
    public long user_id;

    public void save(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString(ApiConstants.ACCESS_TOKEN, access_token);
        editor.putLong(ApiConstants.USER_ID, user_id);
        editor.apply();
    }

    public void restore(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        access_token = prefs.getString(ApiConstants.ACCESS_TOKEN, null);
        user_id = prefs.getLong(ApiConstants.USER_ID, 0);
    }
}
