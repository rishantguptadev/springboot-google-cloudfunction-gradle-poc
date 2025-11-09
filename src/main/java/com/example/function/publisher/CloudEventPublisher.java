package com.example.function.publisher;

import com.google.pubsub.v1.ProjectTopicName;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;

import io.cloudevents.core.message.StructuredMessageWriter;
import io.cloudevents.jackson.JsonFormat;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.provider.EventFormatProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Publishes CloudEvent serialized JSON to a Pub/Sub topic "alpha-final-abc".
 *
 * This class is Spring-managed and constructed via DI. Tests can mock this bean.
 */
@Component
public class CloudEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(CloudEventPublisher.class);

    private final String projectId;
    private final String topicId = "alpha-final-abc";

    public CloudEventPublisher(@Value("${gcp.project.id:${GCP_PROJECT:}}") String projectId) {
        this.projectId = projectId;
    }

    /**
     * Publish a CloudEvent to the configured topic.
     */
    public String publish(CloudEvent event) throws Exception {
        if (projectId == null || projectId.isBlank()) {
            // In local development we might not have a project id; just log and return a dummy id.
            logger.info("Project id not set, skipping actual Pub/Sub publish. CloudEvent: {}", event);
            return "local-skip";
        }

        ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(topicName).build();

            // Serialize CloudEvent to JSON using Jackson format (structured)
            EventFormatProvider provider = EventFormatProvider.getInstance();
            byte[] serialized = provider.resolveFormat(JsonFormat.CONTENT_TYPE).serialize(event);

            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(ByteString.copyFrom(serialized))
                    .putAttributes("content-type", JsonFormat.CONTENT_TYPE)
                    .build();

            ApiFuture<String> future = publisher.publish(pubsubMessage);
            String messageId = future.get();
            logger.info("Published CloudEvent to {} with messageId={}", topicId, messageId);
            return messageId;
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }
}