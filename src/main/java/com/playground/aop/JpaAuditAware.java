package com.playground.aop;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.playground.model.LoginMemberDto;

@Component
public class JpaAuditAware implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
      return Optional.of("anonymous");
    }

    return Optional.of(((LoginMemberDto) authentication.getPrincipal()).getMberId());
  }
}
