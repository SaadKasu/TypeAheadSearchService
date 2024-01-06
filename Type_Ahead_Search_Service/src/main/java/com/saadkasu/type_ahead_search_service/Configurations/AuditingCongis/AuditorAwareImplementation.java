package com.saadkasu.type_ahead_search_service.Configurations.AuditingCongis;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImplementation implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable("Saad Kasu");
    }
}
