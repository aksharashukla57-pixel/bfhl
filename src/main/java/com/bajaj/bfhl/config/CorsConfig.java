package com.bajaj.bfhl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Enterprise CORS Configuration.
 * 
 * Allows frontend applications (such as React, Angular, or Vue hosted on Vercel, Netlify, etc.)
 * to consume this REST API without encountering Cross-Origin Resource Sharing restrictions.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // In production, restrict this to specific origins. For assessment ease, allowing all.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600); // Cache pre-flight response for 1 hour
    }
}
