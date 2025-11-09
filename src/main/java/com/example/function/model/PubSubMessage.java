package com.example.function.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Base64;
import java.util.Map;
import java.nio.charset.StandardCharsets;

/**
 * Minimal representation of a Pub/Sub message for our use.
 */
public class PubSubMessage {
    @JsonProperty("data")
    private String data; // base64 encoded data

    @JsonProperty("attributes")
    private Map<String, String> attributes;

    public String getData() {
        return data;
    }

    public byte[] decodedData() {
        if (data == null) return null;
        return Base64.getDecoder().decode(data);
    }

    public String decodedDataAsString() {
        byte[] bytes = decodedData();
        return bytes == null ? null : new String(bytes, StandardCharsets.UTF_8);
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}