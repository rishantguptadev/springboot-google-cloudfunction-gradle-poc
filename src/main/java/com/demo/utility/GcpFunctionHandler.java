package com.demo.utility;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GcpFunctionHandler implements BackgroundFunction<PubSubMessage> {

    private static final AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Application.class);

    @Override
    public void accept(PubSubMessage message, Context contextInfo) {
        UtilityService service = context.getBean(UtilityService.class);
        service.process(message);
    }
}
