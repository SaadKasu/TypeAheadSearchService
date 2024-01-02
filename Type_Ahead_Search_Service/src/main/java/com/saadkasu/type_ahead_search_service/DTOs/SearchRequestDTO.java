package com.saadkasu.type_ahead_search_service.DTOs;

import lombok.Data;

@Data
public class SearchRequestDTO {
    private double weightage;
    private String word;
}
