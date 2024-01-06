package com.saadkasu.type_ahead_search_service.Services;

import com.saadkasu.type_ahead_search_service.Configurations.CustomMetadatas.NumericValuesMetadata;
import com.saadkasu.type_ahead_search_service.Models.SearchTerm;
import com.saadkasu.type_ahead_search_service.Repositories.SearchTermRepository;
import com.saadkasu.type_ahead_search_service.Stratergies.DecayByFactor;
import com.saadkasu.type_ahead_search_service.Stratergies.IDecayStratergy;
import com.saadkasu.type_ahead_search_service.Utility.Trie.Services.*;
import com.saadkasu.type_ahead_search_service.Utility.GeneralUtilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TypeAheadSearchService implements ISearchService{

    private TrieService trieService;
    private IDecayStratergy decayStratergy;
    private SearchTermRepository searchTermRepository;
    private double defaultWeightage;
    @Autowired
    public TypeAheadSearchService(SearchTermRepository searchTermRepository){
        this.searchTermRepository = searchTermRepository;
        this.decayStratergy = new DecayByFactor();
        defaultWeightage = NumericValuesMetadata.defaultWeightage;
        this.trieService = new TrieService(getAllSearchTerms());
    }
    @Override
    public Optional<List<SearchTerm>> getAllSearchTerms() {
        List<SearchTerm> searchTerms = searchTermRepository.findAll();
        return Optional.ofNullable(searchTerms);
    }

    @Override
    public Optional<SearchTerm> searchForTerm(SearchTerm searchTerm) {
       String word = SearchTermUtility.convertSearchTermToWord(searchTerm);
       SearchTerm searchTermInTrie = trieService.searchForTerm(word,defaultWeightage);
       SearchTerm insertedSearchTerm = searchTermRepository.save(searchTermInTrie);
       return Optional.ofNullable(insertedSearchTerm);
    }

    @Override
    public Optional<List<SearchTerm>> getSuggestions(SearchTerm searchTerm) {
        String searchWord = searchTerm.getWord();
       List<SearchTerm> searchTerms = trieService.getSuggestions(searchWord);
       return Optional.ofNullable(searchTerms);
    }

    @Override
    public Optional<List<SearchTerm>> performDecayOperation() {
       Optional<List<SearchTerm>> optionalSearchTerms = getAllSearchTerms();
       if (optionalSearchTerms.isEmpty())
           return optionalSearchTerms;
       List<SearchTerm> searchTermsAfterDecay = decayStratergy.performDecay(optionalSearchTerms.get());
       searchTermRepository.saveAll(searchTermsAfterDecay);
       return Optional.ofNullable(searchTermsAfterDecay);
    }


}
