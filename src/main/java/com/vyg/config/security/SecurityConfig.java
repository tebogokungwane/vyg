package com.vyg.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("🚀 Initializing SecurityFilterChain...");

        http
                .cors(cors -> cors.configurationSource(request -> {
                    log.info("🌐 CORS check for request: {} {}", request.getMethod(), request.getRequestURI());
                    log.info("🌐 Origin: {}", request.getHeader("Origin"));

                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of("*"));
                    config.setAllowedMethods(List.of("*"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(false);

                    log.info("✅ CORS config applied (ALLOW ALL for testing)");

                    return config;
                }))
                .csrf(csrf -> {
                    log.info("🔒 CSRF disabled");
                    csrf.disable();
                })
                .sessionManagement(session -> {
                    log.info("📦 Stateless session enabled");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(auth -> {
                    log.info("🔐 Configuring request authorization...");

                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    log.info("✅ OPTIONS requests permitted");

                    auth.requestMatchers("/api/member/login").permitAll();
                    log.info("✅ /api/member/login is public");

                    auth.anyRequest().authenticated();
                    log.info("🔒 All other requests require authentication");
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.warn("⚠️ Using NoOpPasswordEncoder (NOT for production)");
        return NoOpPasswordEncoder.getInstance();
    }
}
