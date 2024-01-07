package com.saadkasu.type_ahead_search_service.Controllers;

import com.saadkasu.type_ahead_search_service.DTOs.SearchRequestDTO;
import com.saadkasu.type_ahead_search_service.DTOs.SearchResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

public interface ISearchTermController {
    public ResponseEntity<List<SearchResponseDTO>> getSuggestions(@RequestParam(name = "term") String searchTerm);
    public ResponseEntity<SearchResponseDTO> searchForTerm(@RequestBody SearchRequestDTO requestDTO);
    public ResponseEntity<List<SearchResponseDTO>> getAllSearchTerms();
    public ResponseEntity<List<SearchResponseDTO>> performDecayOperation();
}
