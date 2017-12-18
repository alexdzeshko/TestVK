package com.github.pavelkv96.testvk;

import com.github.pavelkv96.testvk.http.HttpClient;
import com.github.pavelkv96.testvk.http.IHttpClient;
import com.github.pavelkv96.testvk.mocks.Mocks;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ExampleUnitTest {
    private IHttpClient mHttpClient;

    @Before
    public void setUp() throws Exception {
        mHttpClient = mock(HttpClient.class);
    }

    @Test
    public void test() throws IOException, JSONException {
        String mockedInputStream = Mocks.stream("newsfeel1.json");
        when(mHttpClient.request(Matchers.anyString())).thenReturn(mockedInputStream);
        String response = mHttpClient.request("http://myBackend/getUserList");
        JSONObject root = new JSONObject(response);

        Newsfeed h = new Newsfeed().parse(root, false);
        for (NewsItem i : h.items) {
            System.out.println("type         : " + i.type);
            System.out.println("source_id    : " + i.source_id);
            System.out.println("date         : " + i.date);
            System.out.println("post_id      : " + i.post_id);
            System.out.println("post_type    : " + i.post_type);
            //System.out.println("copy_owner_id: " + i.copy_owner_id);
            //System.out.println("copy_post_id : " + i.copy_post_id);
            //System.out.println("copy_text    : " + i.copy_text);
            System.out.println("text         : " + i.text);
            System.out.println("signer_id    : " + i.signer_id);
            System.out.println("copy_history : " + i.copy_history);
            System.out.println("attachments  : " + i.attachments);
            System.out.println("comments     : " + i.comments);
            System.out.println("likes        : " + i.likes);
            System.out.println("reposts      : " + i.reposts + "\n\n");
        }
    }
}