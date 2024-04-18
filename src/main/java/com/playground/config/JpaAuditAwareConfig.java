package com.playground.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "jpaAuditAware")
@Configuration
public class JpaAuditAwareConfig {
}
