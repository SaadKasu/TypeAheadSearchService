package com.saadkasu.type_ahead_search_service.Services;

import com.saadkasu.type_ahead_search_service.Models.SearchTerm;
import com.saadkasu.type_ahead_search_service.Repositories.ISearchTermRepository;
import com.saadkasu.type_ahead_search_service.Stratergies.DecayByFactor;
import com.saadkasu.type_ahead_search_service.Stratergies.IDecayStratergy;
import com.saadkasu.type_ahead_search_service.Utility.GeneralUtilities.SearchTermUtility;
import com.saadkasu.type_ahead_search_service.Utility.Trie.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@EnableScheduling
public class TypeAheadSearchService implements ISearchService{

    private TrieService trieService;
    private IDecayStratergy decayStratergy;
    private ISearchTermRepository searchTermRepository;
    @Autowired
    public TypeAheadSearchService(ISearchTermRepository searchTermRepository){
        this.searchTermRepository = searchTermRepository;
        this.decayStratergy = new DecayByFactor();
        this.trieService = new TrieService(getAllSearchTerms());
    }
    @Override
    public Optional<List<SearchTerm>> getAllSearchTerms() {
        List<SearchTerm> searchTerms = searchTermRepository.findAll();
        return Optional.ofNullable(searchTerms);
    }

    @Override
    public Optional<SearchTerm> searchForTerm(SearchTerm searchTerm) {
       SearchTerm mostRecentSearchTerm = getMostRecentSearchTerm(searchTerm);
       SearchTerm searchTermInTrie = trieService.searchForTerm(mostRecentSearchTerm);
       SearchTerm insertedSearchTerm = saveSearchTermToDatabase(searchTermInTrie);
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

    private SearchTerm saveSearchTermToDatabase(SearchTerm searchTerm){
        return searchTermRepository.save(searchTerm);
    }

    private SearchTerm getMostRecentSearchTerm(SearchTerm searchTerm){
        Optional<SearchTerm> optionalSearchTerm = searchTermRepository.searchByWord(searchTerm.getWord());
        if (optionalSearchTerm.isEmpty())
            return searchTerm;
        return optionalSearchTerm.get();
    }
@Scheduled(cron = "${decay_interval_in_cron_format}")
@Async
protected void scheduledDecayOperation(){
        performDecayOperation();
    }

}
