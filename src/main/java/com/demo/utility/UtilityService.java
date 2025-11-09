package com.demo.utility;

import org.springframework.stereotype.Service;

@Service
public class UtilityService {
    public void process(PubSubMessage message) {
        System.out.println("Processing message: " + message.getData());
    }
}
