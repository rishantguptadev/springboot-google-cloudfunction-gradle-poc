package com.example.function.factory;

import com.example.function.service.EventProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Factory that chooses an EventProcessorService based on the "type" attribute in PubSub message attributes.
 * The factory itself is DI-managed and gets a map of available processors. No manual object creation.
 */
@Component
public class EventProcessorFactory {

    private final Map<String, EventProcessorService> processorsByName;

    @Autowired
    public EventProcessorFactory(Map<String, EventProcessorService> processorsByName) {
        this.processorsByName = processorsByName;
    }

    /**
     * Look up the processor by the "type" attribute and return it.
     * If missing or unknown, throws IllegalArgumentException.
     *
     * @param attributes PubSub attributes map where we read key "type"
     */
    public EventProcessorService forMessage(Map<String, String> attributes) {
        String type = attributes == null ? null : attributes.get("type");
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Missing 'type' attribute in message");
        }

        // Beans are registered with names like "typeAProcessor" etc. We'll expect the map to
        // contain beans keyed by bean name or by type id. For convenience we support both:
        // - exact match to bean name
        // - type-based key naming convention: type -> type + "Processor"
        if (processorsByName.containsKey(type)) {
            return processorsByName.get(type);
        }
        String alt = type + "Processor";
        if (processorsByName.containsKey(alt)) {
            return processorsByName.get(alt);
        }

        // try lower-cased
        String lower = type.toLowerCase();
        if (processorsByName.containsKey(lower)) {
            return processorsByName.get(lower);
        }

        throw new IllegalArgumentException("No processor found for type: " + type);
    }
}