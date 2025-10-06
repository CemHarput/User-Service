package com.caseStudy.user_service.config;

import com.caseStudy.user_service.util.AuditorContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    @Bean
    public AuditorAware<UUID> auditorAware() {
        return () -> Optional.ofNullable(AuditorContext.get());
    }

}
