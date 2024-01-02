package com.saadkasu.type_ahead_search_service.Services;

import com.saadkasu.type_ahead_search_service.Models.SearchTerm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TypeAheadSearchService implements ISearchService{
    @Override
    public Optional<List<SearchTerm>> getAllSearchTerms() {
        return Optional.empty();
    }

    @Override
    public Optional<SearchTerm> searchForTerm(SearchTerm searchTerm) {
        return Optional.empty();
    }

    @Override
    public Optional<List<SearchTerm>> getAllSuggestions(int count) {
        return Optional.empty();
    }

    @Override
    public Optional<List<SearchTerm>> performDecayOperation(double decayFactor) {
        return Optional.empty();
    }
}
