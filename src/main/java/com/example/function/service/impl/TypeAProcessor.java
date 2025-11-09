package com.example.function.service.impl;

import com.example.function.model.PubSubMessage;
import com.example.function.publisher.CloudEventPublisher;
import com.example.function.service.EventProcessorService;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.CloudEvent;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Example processor for messages of type "typeA".
 *
 * The bean name is "typeAProcessor" because Spring's default bean name for this class
 * is typeAProcessor (class name with lower-cased first char). This matches the factory lookup.
 */
@Service("typeAProcessor")
public class TypeAProcessor implements EventProcessorService {

    private final CloudEventPublisher publisher;

    public TypeAProcessor(CloudEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void process(PubSubMessage message) throws Exception {
        byte[] data = message.decodedData();
        String payload = message.decodedDataAsString();

        CloudEvent event = CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withType("com.example.typeA.event")
                .withSource(URI.create("/pubsub/from-typeA"))
                .withTime(OffsetDateTime.now())
                .withData("text/plain", data)
                .build();

        publisher.publish(event);
    }
}