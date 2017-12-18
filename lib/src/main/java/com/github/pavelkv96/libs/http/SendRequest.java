package com.github.pavelkv96.libs.http;

import android.util.Log;

import com.github.pavelkv96.libs.api.Params;
import com.github.pavelkv96.libs.constants.ApiConstants;
import com.github.pavelkv96.libs.errors.KException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SendRequest {
    static final String TAG = "http.Api";

    public JSONObject sendRequest(Params params,String access_token) throws IOException, JSONException, KException {
        String url = getSignedUrl(params,access_token);

        Log.i(TAG, "url=" + url);

        String response = "";
        for (int i = 1; i <= ApiConstants.MAX_TRIES; ++i) {
            try {
                if (i != 1)
                    Log.i(TAG, "try " + i);

                response = new GetPostHttpClient().response(url);//sendRequestInternal(url);
                break;
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
        Log.i(TAG, "response=" + response);
        JSONObject root = new JSONObject(response);
        return root;
    }

    private String getSignedUrl(Params params, String access_token) {
        params.put(ApiConstants.ACCESS_TOKEN,
                access_token
        );
        Log.e(TAG, "||| "+access_token);
        if (!params.contains(ApiConstants.VERSION))
            params.put(ApiConstants.VERSION, ApiConstants.API_VERSION);

        String args = params.getParamsString();
        return ApiConstants.BASE_URL + params.mMethodName + "?" + args;
    }
}
