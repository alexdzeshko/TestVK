package com.github.pavelkv96.testvk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.github.pavelkv96.libs.api.Api;
import com.github.pavelkv96.libs.api.Dialog;
import com.github.pavelkv96.libs.constants.ApiConstants;
import com.github.pavelkv96.libs.constants.Constants;
import com.github.pavelkv96.libs.http.Account;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_LOGIN = 1;


    RecyclerView recyclerView;
    Button authorizeButton;
    Button logoutButton;

    Account account = new Account();
    Api api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();

        //Restoring friend_photo_image_view saved session
        account.restore(this);

        //Если сессия есть создаём API для обращения к серверу
        if (account.access_token != null) {
            api = new Api(account.access_token, Constants.API_ID);
        }

        showButtons();
    }

    private void setupUI() {
        authorizeButton = (Button) findViewById(R.id.authorize);
        logoutButton = (Button) findViewById(R.id.logout);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setStackFromEnd(true);
        //layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        authorizeButton.setOnClickListener(authorizeClick);
        logoutButton.setOnClickListener(logoutClick);
        //postButton.setOnClickListener(postClick);
    }

    private View.OnClickListener authorizeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startLoginActivity();
        }
    };

    private View.OnClickListener logoutClick1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logOut();
        }
    };

    private View.OnClickListener logoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                ArrayList<Dialog/*Message*/> a = api.getMessagesDialogs(0, 50, Constants.EMPTY_STRING, Constants.EMPTY_STRING);
                recyclerView.setAdapter(new DialogsAdapter(MainActivity.this, a));
                /*for (Dialog*//**//*Message*//**//* m : friend_photo_image_view) {
                    Log.e("myLogs", "||| u_id " + String.valueOf(m.getMessageObject().mUserId));
                    Log.e("myLogs", "Body " + String.valueOf(m.getMessageObject().mBody));
                    Log.e("myLogs", "mDate " + String.valueOf(m.getMessageObject().mDate));
                    Log.e("myLogs", "mIsOut " + String.valueOf(m.getMessageObject().mIsOut));
                    Log.e("myLogs", "mAdminId " + String.valueOf(m.getMessageObject().mAdminId));
                    Log.e("myLogs", "mReadState " + String.valueOf(m.getMessageObject().mReadState));
                    Log.e("myLogs", "mTitle " + String.valueOf(m.getMessageObject().mTitle));
                    Log.e("myLogs", "m_id " + String.valueOf(m.getMessageObject().mMessageId));
                    Log.e("myLogs", "attachments " + String.valueOf(m.getInRead()));
                }*/
                //api.sendMessage(Long.parseLong("294948301"),1,text,null,null,null,null,null,null,null,null);
                //createWallPost(account.user_id, text, null, null, false, false, false, null, null, null, 0L, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void startLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_LOGIN) && (resultCode == RESULT_OK)) {
            //Authorized successfully
            account.access_token = data.getStringExtra("token");
            account.user_id = data.getLongExtra(ApiConstants.USER_ID, 0);
            account.save(MainActivity.this);
            api = new Api(account.access_token, Constants.API_ID);
            showButtons();
        }
    }

    private void logOut() {
        api = null;
        account.access_token = null;
        account.user_id = 0;
        showButtons();
    }

    void showButtons() {
        if (api != null) {
            authorizeButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
        } else {
            authorizeButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
        }
    }
}