package com.example.function.service;

import com.example.function.model.PubSubMessage;

/**
 * Processor contract: read the incoming Pub/Sub message, convert to CloudEvent and publish.
 */
public interface EventProcessorService {
    /**
     * Process the incoming Pub/Sub message (read attributes & data, convert to CloudEvent and publish)
     */
    void process(PubSubMessage message) throws Exception;
}