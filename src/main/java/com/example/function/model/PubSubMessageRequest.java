package com.example.function.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Minimal model matching the JSON delivered by Pub/Sub push or the test payload.
 */
public class PubSubMessageRequest {
    @JsonProperty("message")
    private PubSubMessage message;

    public PubSubMessage getMessage() {
        return message;
    }

    public void setMessage(PubSubMessage message) {
        this.message = message;
    }
}