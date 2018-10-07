package com.adrian.mobsters.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

@Configuration
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    @Value("${CORS_ALLOWED_HOST:localhost}")
    private String allowedHost;
    @Value("${CORS_ALLOWED_PORT:8081}")
    private int corsAllowedPort;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(String.format("http://%s:%d", allowedHost, corsAllowedPort))
                .allowedMethods("*");
    }
}
