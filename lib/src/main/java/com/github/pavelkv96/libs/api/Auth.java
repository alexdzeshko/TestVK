package com.github.pavelkv96.libs.api;

import android.util.Log;

import com.github.pavelkv96.libs.constants.ApiConstants;
import com.github.pavelkv96.libs.constants.Constants;
import com.github.pavelkv96.libs.utils.Utils;

import java.net.URLEncoder;

public class Auth {

    private static final String TAG = "Api.Auth";

    public static String getUrl(String pApiId, String pSettings) {
        return Constants.OAUTH_URL +
                "client_id=" + pApiId +
                "&display=mobile&scope=" + pSettings +
                "&redirect_uri=" + URLEncoder.encode(Constants.REDIRECT_URL) +
                "&response_type=token" + "&v=" + URLEncoder.encode(ApiConstants.API_VERSION);
    }

    public static String[] parseRedirectUrl(String pUrl) throws Exception {
        String mAccessToken = Utils.extractPattern(pUrl, "access_token=(.*?)&");
        Log.i(TAG, "access_token=" + mAccessToken);
        String mUserId = Utils.extractPattern(pUrl, "user_id=(\\d*)");
        Log.i(TAG, "user_id=" + mUserId);
        if (mUserId == null || mUserId.length() == 0 || mAccessToken == null || mAccessToken.length() == 0)
            throw new Exception("Failed to parse redirect url " + pUrl);
        return new String[]{mAccessToken, mUserId};
    }
}
