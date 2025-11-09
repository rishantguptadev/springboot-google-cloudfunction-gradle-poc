package com.demo.utility;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import org.springframework.stereotype.Component;

@Component
public class PubSubMessageHandler implements BackgroundFunction<PubSubMessage> {

    private final UtilityService utilityService;

    public PubSubMessageHandler(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @Override
    public void accept(PubSubMessage message, Context context) {
        utilityService.process(message);
    }
}
