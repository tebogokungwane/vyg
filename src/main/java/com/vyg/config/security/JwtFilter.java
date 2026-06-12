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
        log.info("[DEPLOY-CHECK] ✅ JwtFilter (com.vyg.config.security.JwtFilter) instantiated.");
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

        // Let Spring's CorsFilter handle preflight - don't intercept here
        if ("OPTIONS".equalsIgnoreCase(method)) {
            log.debug("[CORS-DEBUG] OPTIONS preflight for {} - passing to Spring CorsFilter", uri);
            filterChain.doFilter(request, response);
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
