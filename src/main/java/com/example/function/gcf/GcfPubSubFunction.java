package com.example.function.gcf;

import com.example.function.model.PubSubMessageRequest;
import com.example.function.service.EventProcessorService;
import com.example.function.factory.EventProcessorFactory;
import com.example.function.spring.SpringContext;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Google Cloud Functions background function entrypoint for Pub/Sub.
 *
 * The function is thin: it delegates to Spring-managed beans. This keeps manual instantiation out of services.
 *
 * To deploy to Cloud Functions, set this class as the entry point:
 * com.example.function.gcf.GcfPubSubFunction
 */
public class GcfPubSubFunction implements BackgroundFunction<PubSubMessageRequest> {
    private static final Logger logger = LoggerFactory.getLogger(GcfPubSubFunction.class);

    @Override
    public void accept(PubSubMessageRequest message, Context context) {
        ApplicationContext spring = SpringContext.get();
        EventProcessorFactory factory = spring.getBean(EventProcessorFactory.class);

        try {
            EventProcessorService processor = factory.forMessage(message.getMessage().getAttributes());
            processor.process(message.getMessage());
            logger.info("Processed message in GCF for attributes: {}", message.getMessage().getAttributes());
        } catch (Exception e) {
            logger.error("Failed to process PubSub message", e);
            throw new RuntimeException(e);
        }
    }
}