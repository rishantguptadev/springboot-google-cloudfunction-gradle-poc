package com.demo.utility;

public class PubSubMessage {
    private String data;
    private String messageId;
    private String publishTime;

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
