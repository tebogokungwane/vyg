package com.vyg.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.io.IOException;

@Configuration
public class CorsFilterConfig {

    private static final Logger log = LoggerFactory.getLogger(CorsFilterConfig.class);

    @Bean
    public FilterRegistrationBean<Filter> corsFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new Filter() {
            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                    throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;

                String origin = request.getHeader("Origin");
                if (origin != null) {
                    response.setHeader("Access-Control-Allow-Origin", origin);
                }
                response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
                response.setHeader("Access-Control-Allow-Headers", "*");
                response.setHeader("Access-Control-Allow-Credentials", "true");

                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    log.info("✅ Preflight handled by CorsFilterConfig: {} | Origin: {}", request.getRequestURI(), origin);
                    response.setStatus(HttpServletResponse.SC_OK);
                    return;
                }

                chain.doFilter(req, res);
            }
        });
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.addUrlPatterns("/*");
        bean.setName("corsFilter");
        log.info("CorsFilterConfig registered at HIGHEST_PRECEDENCE");
        return bean;
    }
}
