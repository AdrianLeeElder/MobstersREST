package com.adrian.mobsters.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    @Value("${CORS_ALLOWED_HOST:localhost}")
    private String allowedHost;
    @Value("${PORT:8080}")
    private int corsAllowedPort;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(String.format("http://%s:%d", allowedHost, corsAllowedPort))
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
