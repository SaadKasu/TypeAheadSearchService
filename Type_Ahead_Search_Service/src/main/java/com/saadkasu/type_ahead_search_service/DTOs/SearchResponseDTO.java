package com.saadkasu.type_ahead_search_service.DTOs;

import lombok.Data;

import java.util.List;
@Data
public class SearchResponseDTO {
    private double weightage;
    private String word;
    private String errorMessage;
}
