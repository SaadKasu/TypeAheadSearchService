package com.saadkasu.type_ahead_search_service.Configurations;

import com.saadkasu.type_ahead_search_service.Configurations.AuditingCongis.AuditorAwareImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class TypeAheadSearchMainConfiguration {
    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImplementation();
    }
}
