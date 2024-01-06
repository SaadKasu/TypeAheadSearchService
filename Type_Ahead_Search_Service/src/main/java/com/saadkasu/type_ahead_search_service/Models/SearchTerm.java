package com.saadkasu.type_ahead_search_service.Models;

import com.saadkasu.type_ahead_search_service.Configurations.AuditingCongis.AuditorAwareImplementation;
import com.saadkasu.type_ahead_search_service.Repositories.SearchTermRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
@EntityListeners(AuditorAwareImplementation.class)
public class SearchTerm extends BaseClass{
    @Column(name = "Search_Word", nullable = false)
    private String word;
    @Column(name = "Weightage", nullable = false)
    private double weightage;
}
