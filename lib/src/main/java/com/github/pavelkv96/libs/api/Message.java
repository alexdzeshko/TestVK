package com.github.pavelkv96.libs.api;

import com.github.pavelkv96.libs.constants.ApiConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public long mDate;
    public long mUserId;
    public long mMessageId;
    public String mTitle;
    public String mBody;
    public boolean mReadState;
    public boolean mIsOut;
    public Long mChatId;
    public ArrayList<Long> mChatMembers;
    public Long mAdminId;

    public static Message parse(JSONObject pMessageJSONObject, boolean from_history, long history_uid, boolean from_chat, long me) throws NumberFormatException, JSONException {
        Message message = new Message();
        if (from_chat) {
            long from_id = pMessageJSONObject.getLong(ApiConstants.USER_ID);
            message.mUserId = from_id;
            message.mIsOut = (from_id == me);
        } else if (from_history) {
            message.mUserId = history_uid;
            Long from_id = pMessageJSONObject.getLong(ApiConstants.FROM_ID);
            message.mIsOut = !(from_id == history_uid);
        } else {
            message.mUserId = pMessageJSONObject.getLong(ApiConstants.USER_ID);
            message.mIsOut = pMessageJSONObject.optInt(ApiConstants.OUT) == 1;
        }

        message.mMessageId = pMessageJSONObject.optLong(ApiConstants.ID);
        message.mDate = pMessageJSONObject.optLong(ApiConstants.DATE);
        message.mTitle = pMessageJSONObject.optString(ApiConstants.TITLE);
        message.mBody = pMessageJSONObject.optString(ApiConstants.BODY);
        message.mReadState = (pMessageJSONObject.optInt(ApiConstants.READ_STATE) == 1);

        if (pMessageJSONObject.has(ApiConstants.CHAT_ID))
            message.mChatId = pMessageJSONObject.getLong(ApiConstants.CHAT_ID);

        JSONArray tmp = pMessageJSONObject.optJSONArray(ApiConstants.CHAT_ACTIVE);

        if (tmp != null && tmp.length() != 0) {
            message.mChatMembers = new ArrayList<>();
            for (int i = 0; i < tmp.length(); ++i)
                message.mChatMembers.add(tmp.getLong(i));
        }

        return message;
    }
}
