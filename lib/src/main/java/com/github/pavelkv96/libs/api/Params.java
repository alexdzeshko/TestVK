package com.github.pavelkv96.libs.api;

import com.github.pavelkv96.libs.constants.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

public class Params {
    private HashMap<String, String> argumentsForRequestString = new HashMap<>();
    public String mMethodName;

    public Params(String pMethodName) {
        this.mMethodName = pMethodName;
    }

    public boolean contains(String pVersionName) {
        return argumentsForRequestString.containsKey(pVersionName);
    }

    public void put(String pName, String pValue) {
        if (pValue == null || pValue.length() == 0)
            return;
        argumentsForRequestString.put(pName, pValue);
    }

    public void put(String pName, Long pValue) {
        if (pValue == null)
            return;
        argumentsForRequestString.put(pName, Long.toString(pValue));
    }

    public void put(String pName, Integer pValue) {
        if (pValue == null)
            return;
        argumentsForRequestString.put(pName, Integer.toString(pValue));
    }

    public void putDouble(String pName, double pValue) {
        argumentsForRequestString.put(pName, Double.toString(pValue));
    }

    public String getParamsString() {
        String params = Constants.EMPTY_STRING;
        try {
            for (Entry<String, String> entry : argumentsForRequestString.entrySet()) {
                if (params.length() != 0)
                    params += "&";
                params += (entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), Constants.UTF_8));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return params;
    }
}
