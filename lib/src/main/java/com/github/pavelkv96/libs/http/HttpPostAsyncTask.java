package com.github.pavelkv96.libs.http;

import android.os.AsyncTask;

import com.github.pavelkv96.libs.errors.WrongResponseCodeException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpPostAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        URL urlAddress;
        HttpURLConnection connection = null;
        String response = null;

        try {
            urlAddress = new URL(url);
            connection = (HttpURLConnection) urlAddress.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setUseCaches(false);
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();

            if (code == -1)
                throw new WrongResponseCodeException("Network error");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            reader.close();
            response = stringBuilder.toString();
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response;
    }
}
