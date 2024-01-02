package com.saadkasu.type_ahead_search_service.Controllers;

import com.saadkasu.type_ahead_search_service.DTOs.SearchRequestDTO;
import com.saadkasu.type_ahead_search_service.DTOs.SearchResponseDTO;
import com.saadkasu.type_ahead_search_service.Services.ISearchService;
import com.saadkasu.type_ahead_search_service.Services.TypeAheadSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TypeAheadSearch")
public class TypeAheadSearchController implements ISearchController {

    private final ISearchService searchService;
    @Autowired
    TypeAheadSearchController(TypeAheadSearchService searchService){
        this.searchService = searchService;
    }
    @GetMapping("/GetSuggestion")
    @Override
    public ResponseEntity<List<SearchResponseDTO>> getAllSuggestions(@RequestParam (name = "count") int count) {
        return null;
    }
    @PostMapping("/SearchTerm")
    @Override
    public ResponseEntity<SearchResponseDTO> searchForTerm(@RequestBody SearchRequestDTO requestDTO) {
        return null;
    }
    @GetMapping("/AllTerms")
    @Override
    public ResponseEntity<List<SearchResponseDTO>> getAllSearchTerms() {
        return null;
    }
    @PostMapping("PerformDecay")
    @Override
    public ResponseEntity<List<SearchResponseDTO>> performDecayOperation(@PathVariable double decayFactor) {
        return null;
    }
}
