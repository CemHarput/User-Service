package com.caseStudy.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditingConfig {
    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.of("system");
    }
}
