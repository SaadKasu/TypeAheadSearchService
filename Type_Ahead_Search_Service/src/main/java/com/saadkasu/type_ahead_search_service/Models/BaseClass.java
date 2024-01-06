package com.saadkasu.type_ahead_search_service.Models;

import com.saadkasu.type_ahead_search_service.Configurations.AuditingCongis.AuditorAwareImplementation;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
@MappedSuperclass
@Data
public class BaseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @CreatedBy
    @Column(name = "Created_By", nullable = false, updatable = false)
    private String createdBy;
    @LastModifiedBy
    @Column(name = "Last_Modified_By", nullable = false)
    private String lastModifiedBy;
    @CreatedDate
    @Column(name = "Created_Date", nullable = false, updatable = false)
    private long createdDate;
    @LastModifiedDate
    @Column(name = "Last_Modified_Date", nullable = false)
    private long lastModifiedDate;

}
