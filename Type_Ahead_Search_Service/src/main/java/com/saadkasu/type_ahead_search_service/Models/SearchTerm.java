package com.saadkasu.type_ahead_search_service.Models;

import jakarta.persistence.Entity;
import lombok.Data;
@Entity
@Data
public class SearchTerm extends BaseClass{
    private String word;
    private double weightage;
}
