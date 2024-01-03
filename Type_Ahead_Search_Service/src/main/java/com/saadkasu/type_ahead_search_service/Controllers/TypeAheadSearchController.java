package com.saadkasu.type_ahead_search_service.Controllers;

import com.saadkasu.type_ahead_search_service.DTOs.SearchRequestDTO;
import com.saadkasu.type_ahead_search_service.DTOs.SearchResponseDTO;
import com.saadkasu.type_ahead_search_service.Models.SearchTerm;
import com.saadkasu.type_ahead_search_service.Services.ISearchService;
import com.saadkasu.type_ahead_search_service.Services.TypeAheadSearchService;
import com.saadkasu.type_ahead_search_service.Utility.SearchTermUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<SearchResponseDTO>> getSuggestions() {
        Optional<List<SearchTerm>> searchTerms = searchService.getSuggestions();
        List<SearchResponseDTO> responseDTOS = SearchTermUtility.convertListOfSearchTermsToDTOs(searchTerms);
        return new ResponseEntity<>(responseDTOS, HttpStatusCode.valueOf(200));
    }
    @PostMapping("/SearchTerm")
    @Override
    public ResponseEntity<SearchResponseDTO> searchForTerm(@RequestBody SearchRequestDTO requestDTO) {
        SearchTerm searchTerm = SearchTermUtility.convertSingleDtoToSearchTerm(requestDTO);
        Optional<SearchTerm> optionalSearchTerm = searchService.searchForTerm(searchTerm);
        SearchResponseDTO responseDTO = SearchTermUtility.convertSingleSearchTermToDTO(optionalSearchTerm);
        return new ResponseEntity<>(responseDTO,HttpStatusCode.valueOf(200));
    }
    @GetMapping("/GetAllTerms")
    @Override
    public ResponseEntity<List<SearchResponseDTO>> getAllSearchTerms() {
        Optional<List<SearchTerm>> optionalSearchTerms = searchService.getAllSearchTerms();
        List<SearchResponseDTO> responseDTOS = SearchTermUtility.convertListOfSearchTermsToDTOs(optionalSearchTerms);
        return new ResponseEntity<>(responseDTOS,HttpStatusCode.valueOf(200));
    }
    @PostMapping("/PerformDecay")
    @Override
    public ResponseEntity<List<SearchResponseDTO>> performDecayOperation(@PathVariable double decayFactor) {
        Optional<List<SearchTerm>> optionalSearchTerms = searchService.performDecayOperation(decayFactor);
        List<SearchResponseDTO> responseDTOS = SearchTermUtility.convertListOfSearchTermsToDTOs(optionalSearchTerms);
        return new ResponseEntity<>(responseDTOS,HttpStatusCode.valueOf(200));
    }
}
