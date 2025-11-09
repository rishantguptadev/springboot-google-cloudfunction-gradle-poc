package com.example.function.spring;

import com.example.function.Application;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.boot.SpringApplication;

/**
 * Helper to start/hold Spring context when used from non-Spring-managed entrypoints (e.g. GCF runtime).
 * This is a static holder that starts the Spring context once using the main Application class.
 */
@Component
public class SpringContext {
    private static volatile ConfigurableApplicationContext context;

    public static synchronized ConfigurableApplicationContext get() {
        if (context == null) {
            // Use the Application.class to start Spring correctly (avoids calling the non-static overload)
            context = SpringApplication.run(Application.class);
        }
        return context;
    }
}