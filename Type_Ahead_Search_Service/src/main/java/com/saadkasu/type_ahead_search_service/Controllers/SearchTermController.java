package com.saadkasu.type_ahead_search_service.Controllers;

import com.saadkasu.type_ahead_search_service.DTOs.SearchRequestDTO;
import com.saadkasu.type_ahead_search_service.DTOs.SearchResponseDTO;
import com.saadkasu.type_ahead_search_service.Models.SearchTerm;
import com.saadkasu.type_ahead_search_service.Services.ISearchTermService;
import com.saadkasu.type_ahead_search_service.Services.SearchTermService;
import com.saadkasu.type_ahead_search_service.Utility.GeneralUtilities.SearchTermUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/TypeAheadSearch")
public class SearchTermController implements ISearchTermController {

    private final ISearchTermService searchService;
    @Autowired
    public SearchTermController(SearchTermService searchService){
        this.searchService = searchService;
    }
    @GetMapping("/GetSuggestion")
    @Override
    public ResponseEntity<List<SearchResponseDTO>> getSuggestions(@RequestParam(name = "term") String searchWord) {
        SearchTerm searchTerm = SearchTermUtility.convertStringToSearchTerm(searchWord);
        Optional<List<SearchTerm>> searchTerms = searchService.getSuggestions(searchTerm);
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
    @GetMapping("/PerformDecay")
    @Override
    public ResponseEntity<List<SearchResponseDTO>> performDecayOperation() {
        Optional<List<SearchTerm>> optionalSearchTerms = searchService.performDecayOperation();
        List<SearchResponseDTO> responseDTOS = SearchTermUtility.convertListOfSearchTermsToDTOs(optionalSearchTerms);
        return new ResponseEntity<>(responseDTOS,HttpStatusCode.valueOf(200));
    }
}
