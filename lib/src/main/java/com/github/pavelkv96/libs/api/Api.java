package com.github.pavelkv96.libs.api;

import com.github.pavelkv96.libs.constants.ApiConstants;
import com.github.pavelkv96.libs.errors.KException;
import com.github.pavelkv96.libs.http.SendRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Api {
    static final String TAG = "Api.Api";

    private String mAccessToken;
    private String mApiId;

    public Api(String pAccessToken, String pApiId) {
        this.mAccessToken = pAccessToken;
        this.mApiId = pApiId;
    }

    private ArrayList<Dialog> parseDialogs(JSONArray dialogItems, boolean from_history, long history_uid, boolean from_chat, long me) throws JSONException {
        ArrayList<Dialog> dialogItem = new ArrayList<>();
        if (dialogItems != null) {
            int category_count = dialogItems.length();
            for (int i = 0; i < category_count; ++i) {
                JSONObject jsonDialogs = dialogItems.getJSONObject(i);

                Dialog dialog = new Dialog(
                        jsonDialogs.optInt("unread"),
                        Message.parse(jsonDialogs.optJSONObject("message"), from_history, history_uid, from_chat, me),
                        jsonDialogs.optInt("in_read"),
                        jsonDialogs.optInt("out_read")
                );
                //dialog.setUnRead(jsonDialogs.optInt("unread"));
                //dialog.setMessageObject(Message.parse(jsonDialogs.optJSONObject("message"), from_history, history_uid, from_chat, me));
                //dialog.setInRead(jsonDialogs.optInt("in_read"));
                //dialog.setOutRead(jsonDialogs.optInt("out_read"));
                dialogItem.add(dialog);
            }
        }
        return dialogItem;
    }

    public ArrayList<Dialog> getMessagesDialogs(long offset, int count, String captcha_key, String captcha_sid) throws IOException, JSONException, KException {
        Params params = new Params("messages.getDialogs");
        if (offset != 0)
            params.put(ApiConstants.OFFSET, offset);
        if (count != 0)
            params.put(ApiConstants.COUNT, count);

        //Надо задать как параметр
        params.put(ApiConstants.PREVIEW_LENGTH, "20");

        JSONObject root = new SendRequest().sendRequest(params, mAccessToken);
        JSONObject response = root.optJSONObject(ApiConstants.RESPONSE);
        JSONArray dialogItems = response.optJSONArray(ApiConstants.ITEMS);
        return parseDialogs(dialogItems, false, 0, false, 0);
    }
}