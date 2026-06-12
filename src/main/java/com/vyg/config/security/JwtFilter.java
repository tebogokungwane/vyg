package com.vyg.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String origin = request.getHeader("Origin");

        log.debug("[CORS-DEBUG] Incoming: {} {} | Origin: {} | Headers: {}", method, uri, origin, request.getHeader("Access-Control-Request-Headers"));

        // FIX: Handle Preflight requests cleanly by responding 200 OK immediately
        if ("OPTIONS".equalsIgnoreCase(method)) {
            log.info("[CORS-DEBUG] Preflight OPTIONS for {} | Origin: {} | Request-Method: {} | Request-Headers: {}",
                    uri, origin,
                    request.getHeader("Access-Control-Request-Method"),
                    request.getHeader("Access-Control-Request-Headers"));
            response.setHeader("Access-Control-Allow-Origin", origin != null ? origin : "https://onrender.com");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Cache-Control");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_OK);
            log.info("[CORS-DEBUG] Preflight response sent with Allow-Origin: {}", origin);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                log.debug("Valid JWT for user: {} on {} {}", email, method, uri);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                Collections.emptyList()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.warn("Invalid JWT token on {} {}", method, uri);
            }
        } else {
            log.debug("No Authorization header on {} {}", method, uri);
        }

        filterChain.doFilter(request, response);
    }
}
