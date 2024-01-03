package com.saadkasu.type_ahead_search_service.Utility.Trie;

import com.saadkasu.type_ahead_search_service.CustomMetadatas.DecayFactorMetadata;
import com.saadkasu.type_ahead_search_service.CustomMetadatas.NumberOfSuggestionsMetadata;
import com.saadkasu.type_ahead_search_service.Helpers.ISortHelper;
import com.saadkasu.type_ahead_search_service.Helpers.SortSuggestion;
import com.saadkasu.type_ahead_search_service.Stratergies.DecayByFactor;
import com.saadkasu.type_ahead_search_service.Stratergies.IDecayStratergy;
import org.springframework.stereotype.Service;

import com.saadkasu.type_ahead_search_service.Models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TrieService {
    private Trie trie;

    private IDecayStratergy decayStratergy;

    private static int noOfSuggestions;
    private ISortHelper sortHelper;
    private ArrayList<SearchTerm> allSearchTerms;
    public TrieService(){
        initializeTrie();
        noOfSuggestions = NumberOfSuggestionsMetadata.noOfSuggestions;
        sortHelper = new SortSuggestion();
        allSearchTerms = new ArrayList<>();
        decayStratergy = new DecayByFactor();
    }

    private void initializeTrie(){
        this.trie = new Trie();
    }
    public SearchTerm searchForTerm(String term, int index, Trie node){
        if (index == term.length()){
            SearchTerm searchTerm;
            if (node.getStateOfTrie() == TrieState.WORD_ENDS){
                searchTerm = node.getSearchTerm();
                searchTerm.setWeightage(searchTerm.getWeightage() + 1);
            }
            else{
                node.setStateOfTrie(TrieState.WORD_ENDS);
                searchTerm = new SearchTerm();
                searchTerm.setWord(term);
                searchTerm.setWeightage(1);
                allSearchTerms.add(searchTerm);
            }
            adjustTopSuggestionsAtNode(searchTerm, node.getTopSuggestions());
            return searchTerm;
        }
        char ch = term.charAt(index);
        if (node.getNextTrieNodes()[ch] == null)
            node.getNextTrieNodes()[ch] = new Trie();
        SearchTerm searchTerm = searchForTerm(term,index + 1,node.getNextTrieNodes()[ch]);
        adjustTopSuggestionsAtNode(searchTerm, node.getTopSuggestions());
        return searchTerm;
    }

    private void adjustTopSuggestionsAtNode(SearchTerm searchTerm, ArrayList<SearchTerm> topSuggestions){
        if (searchTermIsPresentAtNode(searchTerm,topSuggestions)){
            topSuggestions.sort(sortHelper);
        }
        else if (topSuggestions.size() < noOfSuggestions){
            topSuggestions.add(searchTerm);
            topSuggestions.sort(sortHelper);
        }
        else{
            SearchTerm leastFrequentSearchTerm = topSuggestions.get(noOfSuggestions - 1);
            if (leastFrequentSearchTerm.getWeightage() < searchTerm.getWeightage() || (leastFrequentSearchTerm.getWeightage() == searchTerm.getWeightage() && searchTerm.getWord().compareTo(leastFrequentSearchTerm.getWord()) > 0 )){
                topSuggestions.set(noOfSuggestions - 1, searchTerm);
                topSuggestions.sort(sortHelper);
            }
        }
    }

    private boolean searchTermIsPresentAtNode(SearchTerm searchTerm, ArrayList<SearchTerm> topSuggestions){
        for (SearchTerm currentSuggestion : topSuggestions){
            if (currentSuggestion.getWord().equals(searchTerm.getWord()))
                return true;
        }
        return false;
    }

    public ArrayList<SearchTerm> getSuggestions()

    public ArrayList<String> getSuggestions(String term, int index, Trie node){
        if (index == term.length()){
            ArrayList<String> topSuggestions = new ArrayList<>();
            for (int i = 0 ; i<node.getTopSuggestions().size(); i++){
                topSuggestions.add(node.getTopSuggestions().get(i).getWord());
            }
            return topSuggestions;
        }
        char ch = term.charAt(index);
        if (node.getNextTrieNodes()[ch] == null){
            ArrayList<String> topSuggestions = new ArrayList<>();
            return topSuggestions;
        }
        return getSuggestions(term,index + 1,node.getNextTrieNodes()[ch]);
    }

    public List<SearchTerm> getAllSearchTerms(){
        return this.allSearchTerms;
    }

    public List<SearchTerm> performDecayOperation(){
        decayStratergy.performDecay();
        return getAllSearchTerms();
    }

}
