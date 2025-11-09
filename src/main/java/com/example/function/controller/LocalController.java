package com.example.function.controller;

import com.example.function.model.PubSubMessage;
import com.example.function.model.PubSubMessageRequest;
import com.example.function.service.EventProcessorService;
import com.example.function.factory.EventProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Local REST controller to simulate a Pub/Sub trigger during development.
 * Accepts the same JSON payload structure as the GCF Pub/Sub push messages.
 *
 * Example:
 * POST /local/publish
 * {
 *   "message": {
 *     "data": "base64-encoded",
 *     "attributes": {"type":"typeA"}
 *   }
 * }
 */
@RestController
@RequestMapping("/local")
public class LocalController {

    private final EventProcessorFactory factory;

    @Autowired
    public LocalController(EventProcessorFactory factory) {
        this.factory = factory;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody PubSubMessageRequest request) throws Exception {
        PubSubMessage msg = request.getMessage();
        EventProcessorService processor = factory.forMessage(msg.getAttributes());
        processor.process(msg);
        return ResponseEntity.ok("published");
    }

    // Small convenience test endpoint
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}