package com.github.pavelkv96.libs.errors;

import com.github.pavelkv96.libs.constants.ApiConstants;

import java.io.IOException;

public class Exceptions {
    public void processNetworkException(int i, IOException ex) throws IOException {
        ex.printStackTrace();
        if (i == ApiConstants.MAX_TRIES)
            throw ex;
    }
}
