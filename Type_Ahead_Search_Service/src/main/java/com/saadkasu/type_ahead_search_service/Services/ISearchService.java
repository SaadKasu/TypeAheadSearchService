package com.saadkasu.type_ahead_search_service.Services;

import com.saadkasu.type_ahead_search_service.Models.SearchTerm;

import java.util.*;

public interface ISearchService {
    public Optional<List<SearchTerm>> getAllSearchTerms();
    public Optional<SearchTerm> searchForTerm(SearchTerm searchTerm);
    public Optional<List<SearchTerm>> getAllSuggestions(int count);
    public Optional<List<SearchTerm>> performDecayOperation(double decayFactor);
}
