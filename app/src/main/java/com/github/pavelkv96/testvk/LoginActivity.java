package com.github.pavelkv96.testvk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.pavelkv96.libs.api.Auth;
import com.github.pavelkv96.libs.constants.Constants;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";

    WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        webview = findViewById(R.id.vk_web_view);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearCache(true);
        webview.setWebViewClient(new VkWebViewClient());

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        String url = Auth.getUrl(Constants.API_ID, Constants.SETTINGS_PERMISSIONS);

        Log.e(TAG, url);
        webview.loadUrl(url);
    }

    private class VkWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            parseUrl(url);
        }

    }

    private void parseUrl(String url) {
        Intent intent = new Intent();
        try {
            if (url == null) {
                return;
            }

            Log.i(TAG, "url=" + url);
            if (url.startsWith(Constants.REDIRECT_URL)) {
                if (!url.contains("error=")) {
                    String[] auth = Auth.parseRedirectUrl(url);
                    intent.putExtra("token", auth[0]);
                    intent.putExtra("user_id", Long.parseLong(auth[1]));
                    setResult(Activity.RESULT_OK, intent);
                }
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}