package com.saadkasu.type_ahead_search_service.Utility.Trie.Services;

import com.saadkasu.type_ahead_search_service.Configurations.CustomMetadatas.NumberOfSuggestionsMetadata;
import com.saadkasu.type_ahead_search_service.Utility.Helpers.ISortHelper;
import com.saadkasu.type_ahead_search_service.Utility.Helpers.SortSuggestion;
import com.saadkasu.type_ahead_search_service.Utility.Trie.Models.Trie;
import com.saadkasu.type_ahead_search_service.Utility.Trie.Models.TrieState;
import org.springframework.stereotype.Service;

import com.saadkasu.type_ahead_search_service.Models.*;

import java.util.*;

@Service
public class TrieService {
    private Trie trie;
    private static int noOfSuggestions;
    private ISortHelper sortHelper;
    public TrieService(){
        initializeTrie();
        noOfSuggestions = NumberOfSuggestionsMetadata.noOfSuggestions;
        sortHelper = new SortSuggestion();
    }

    private void initializeTrie(){
        this.trie = new Trie();
    }
    public SearchTerm searchForTerm (String word){
        return traverseWordAndIncrementWeightage(word,0,trie);
    }
    private SearchTerm traverseWordAndIncrementWeightage(String term, int index, Trie node){
        if (index == term.length()){
            SearchTerm searchTerm = getSearchTermAtNode(term, node);
            searchTerm.setWeightage(searchTerm.getWeightage() + 1);
            adjustTopSuggestionsAtNode(searchTerm, node.getTopSuggestions());
            return searchTerm;
        }
        char ch = term.charAt(index);
        if (node.getNextTrieNodes()[ch] == null)
            node.getNextTrieNodes()[ch] = new Trie();
        SearchTerm searchTerm = traverseWordAndIncrementWeightage(term,index + 1,node.getNextTrieNodes()[ch]);
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

    private SearchTerm getSearchTermAtNode(String term, Trie node){
        SearchTerm searchTerm;
        if (node.getStateOfTrie() == TrieState.WORD_ENDS){
            searchTerm = node.getSearchTerm();
        }
        else{
            searchTerm = createSearchTerm(term);
            node.setStateOfTrie(TrieState.WORD_ENDS);
            node.setSearchTerm(searchTerm);
        }
        return searchTerm;
    }

    private boolean searchTermIsPresentAtNode(SearchTerm searchTerm, ArrayList<SearchTerm> topSuggestions){
        for (SearchTerm currentSuggestion : topSuggestions){
            if (currentSuggestion.getWord().equals(searchTerm.getWord()))
                return true;
        }
        return false;
    }

    public ArrayList<SearchTerm> getSuggestions(String word){
        return traverseWordAndGetSuggestions(word,0,trie);
    }

    private ArrayList<SearchTerm> traverseWordAndGetSuggestions(String term, int index, Trie node){
        if (index == term.length()){
            return node.getTopSuggestions();
        }
        char ch = term.charAt(index);
        if (node.getNextTrieNodes()[ch] == null){
            return new ArrayList<>();
        }
        return traverseWordAndGetSuggestions(term,index + 1,node.getNextTrieNodes()[ch]);
    }

    private SearchTerm createSearchTerm(String word){
        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setWord(word);
        searchTerm.setWeightage(0);
        return searchTerm;
    }

}
