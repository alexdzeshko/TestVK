package com.github.pavelkv96.libs.http;

public class GetPostHttpClient implements IHttpClient {

    @Override
    public String response(String url) {
        String result = null;
        try {
            result = new HttpPostAsyncTask().execute(url).get();
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }
}
