package com.github.pavelkv96.libs.errors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Errors {

    public void checkError(JSONObject root, String url) throws JSONException, KException {
        if (!root.isNull("error")) {
            JSONObject error = root.getJSONObject("error");
            int code = error.getInt("error_code");
            String message = error.getString("error_msg");
            KException e = new KException(code, message, url);
            if (code == 14) {
                e.captcha_img = error.optString("captcha_img");
                e.captcha_sid = error.optString("captcha_sid");
            }
            if (code == 17)
                e.redirect_uri = error.optString("redirect_uri");
            throw e;
        }
        if (!root.isNull("execute_errors")) {
            JSONArray errors = root.getJSONArray("execute_errors");
            if (errors.length() == 0)
                return;
            //only first error is processed if there are multiple
            JSONObject error = errors.getJSONObject(0);
            int code = error.getInt("error_code");
            String message = error.getString("error_msg");
            KException e = new KException(code, message, url);
            if (code == 14) {
                e.captcha_img = error.optString("captcha_img");
                e.captcha_sid = error.optString("captcha_sid");
            }
            if (code == 17)
                e.redirect_uri = error.optString("redirect_uri");
            throw e;
        }
    }
}
