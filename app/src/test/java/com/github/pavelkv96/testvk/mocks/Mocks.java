package com.github.pavelkv96.testvk.mocks;

import junit.framework.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class Mocks {
    public static String stream(final String pName) throws IOException {
        final InputStream resourceAsStream = Mocks.class.getClassLoader().getResourceAsStream(pName);
        Assert.assertNotNull("resource it's null", resourceAsStream);
        return convertStreamToString(resourceAsStream);
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        InputStreamReader r = new InputStreamReader(is);
        StringWriter sw = new StringWriter();
        char[] buffer = new char[1024];
        try {
            for (int n; (n = r.read(buffer)) != -1; )
                sw.write(buffer, 0, n);
        } finally {
            try {
                is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.toString();
    }
}