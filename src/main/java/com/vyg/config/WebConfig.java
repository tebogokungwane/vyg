package com.vyg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                log.info("✅ Initializing WebMvcConfigurer for CORS");
                System.out.println("✅ WebConfig: CORS setup active for http://localhost:3000");
                log.info("✅ Configuring CORS for React app at http://localhost:3000");
                log.info("🔹 Allowed Methods: GET, POST, PUT, DELETE, OPTIONS");
                log.info("🔹 Allowed Headers: *");
                log.info("🔹 Allow Credentials: true");


                registry.addMapping("/**") // Allow all endpoints
                        .allowedOrigins("http://localhost:3000") // React frontend

                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}

