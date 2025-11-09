package com.demo.utility;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PubSubMessageHandler implements BackgroundFunction<PubSubMessage> {

    private static final AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Application.class);

    private UtilityService service;
    
    @Override
    public void accept(PubSubMessage message, Context contextInfo) {
        service = context.getBean(UtilityService.class);
        service.process(message);
    }
}
