package com.caseStudy.user_service.config;

import com.caseStudy.user_service.util.AuditorContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(0)
public class AuditorFilter extends OncePerRequestFilter {
    private static final String HEADER = "X-Acting-User";
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        try {
            String raw = req.getHeader(HEADER);
            if (raw != null && !raw.isBlank()) {
                try { AuditorContext.set(UUID.fromString(raw)); } catch (IllegalArgumentException ignored) {}
            }
            chain.doFilter(req, res);
        } finally {
            AuditorContext.clear();
        }
    }
}
