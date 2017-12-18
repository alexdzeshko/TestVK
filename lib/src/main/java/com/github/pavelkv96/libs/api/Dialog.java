package com.github.pavelkv96.libs.api;

import java.io.Serializable;

public class Dialog implements Serializable {
    private Integer mUnread;
    private Message mMessageObject;
    private Integer mInRead;
    private Integer mOutRead;

    public Dialog() {
    }

    public Dialog(Integer pUnread, Message pMessageObject, Integer pInRead, Integer pOutRead) {
        this.mUnread = pUnread;
        this.mMessageObject = pMessageObject;
        this.mInRead = pInRead;
        this.mOutRead = pOutRead;
    }

    public Integer getUnRead() {
        return mUnread;
    }

    public void setUnRead(Integer unread) {
        this.mUnread = unread;
    }

    public Message getMessageObject() {
        return mMessageObject;
    }

    public void setMessageObject(Message mMessageObject) {
        this.mMessageObject = mMessageObject;
    }

    public long getInRead() {
        return mInRead;
    }

    public void setInRead(Integer in_read) {
        this.mInRead = in_read;
    }

    public long getOutRead() {
        return mOutRead;
    }

    public void setOutRead(Integer out_read) {
        this.mOutRead = out_read;
    }

}
