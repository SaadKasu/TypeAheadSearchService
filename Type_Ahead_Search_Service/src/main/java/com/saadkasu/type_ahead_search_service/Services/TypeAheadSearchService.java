package com.saadkasu.type_ahead_search_service.Services;

import com.saadkasu.type_ahead_search_service.Models.SearchTerm;
import com.saadkasu.type_ahead_search_service.Utility.Trie.Trie;
import com.saadkasu.type_ahead_search_service.Utility.Trie.TrieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TypeAheadSearchService implements ISearchService{

    private TrieService trieService;
    @Autowired
    public TypeAheadSearchService(TrieService trieService){
        this.trieService = trieService;
    }
    @Override
    public Optional<List<SearchTerm>> getAllSearchTerms() {

    }

    @Override
    public Optional<SearchTerm> searchForTerm(SearchTerm searchTerm) {
        return Optional.empty();
    }

    @Override
    public Optional<List<SearchTerm>> getSuggestions() {
        return Optional.empty();
    }

    @Override
    public Optional<List<SearchTerm>> performDecayOperation(double decayFactor) {
        return Optional.empty();
    }
}
