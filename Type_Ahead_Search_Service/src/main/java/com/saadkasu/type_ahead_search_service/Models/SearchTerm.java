package com.saadkasu.type_ahead_search_service.Models;

import com.saadkasu.type_ahead_search_service.Configurations.AuditingCongis.AuditorAwareImplementation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Data;

@Entity(name = "Search_Terms")
@Data
@EntityListeners(AuditorAwareImplementation.class)
public class SearchTerm extends BaseClass{
    @Column(name = "Search_Word", nullable = false)
    private String word;
    @Column(name = "Weightage", nullable = false)
    private double weightage;
}
