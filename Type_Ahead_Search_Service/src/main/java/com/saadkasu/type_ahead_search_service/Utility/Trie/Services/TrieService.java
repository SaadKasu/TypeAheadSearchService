package com.saadkasu.type_ahead_search_service.Utility.Trie.Services;

import com.saadkasu.type_ahead_search_service.Configurations.CustomMetadatas.NumericValuesMetadata;
import com.saadkasu.type_ahead_search_service.Utility.GeneralUtilities.SearchTermUtility;
import com.saadkasu.type_ahead_search_service.Utility.Helpers.ISortHelper;
import com.saadkasu.type_ahead_search_service.Utility.Helpers.SortSuggestion;
import com.saadkasu.type_ahead_search_service.Utility.Trie.Models.Trie;
import com.saadkasu.type_ahead_search_service.Utility.Trie.Models.TrieOperation;
import com.saadkasu.type_ahead_search_service.Utility.Trie.Models.TrieState;
import org.springframework.stereotype.Service;

import com.saadkasu.type_ahead_search_service.Models.*;

import java.util.*;

@Service
public class TrieService {
    private Trie trie;
    private static int noOfSuggestions;
    private ISortHelper sortHelper;
    private double defaultWeightage;
    public TrieService(Optional<List<SearchTerm>> existingSearchTerms){
        noOfSuggestions = NumericValuesMetadata.noOfSuggestions;
        sortHelper = new SortSuggestion();
        defaultWeightage = NumericValuesMetadata.defaultWeightage;
        buildTrie(existingSearchTerms);
    }

    private void buildTrie(Optional<List<SearchTerm>> existingSearchTerms){
        this.trie = new Trie();
        if (existingSearchTerms.isEmpty() || existingSearchTerms.get().isEmpty())
            return;
        for (SearchTerm searchTerm : existingSearchTerms.get()){
            initializeExistingSearchTermsFromDatabase(searchTerm);
        }
    }

    private void initializeExistingSearchTermsFromDatabase(SearchTerm searchTerm){
        traverseTrieForSearchTerm(searchTerm,0,trie, TrieOperation.INITIALIZE);
    }

    private SearchTerm traverseTrieForSearchTerm(SearchTerm searchTerm,int index, Trie node,TrieOperation trieOperation){
        if (index == searchTerm.getWord().length()){
            searchTerm = insertOrUpdateSearchTerm(searchTerm,node,trieOperation);
            adjustTopSuggestionsAtNode(searchTerm, node.getTopSuggestions());
            return searchTerm;
        }
        char ch = searchTerm.getWord().charAt(index);
        if (node.getNextTrieNodes()[ch] == null)
            node.getNextTrieNodes()[ch] = new Trie();
        searchTerm = traverseTrieForSearchTerm(searchTerm,index + 1,node.getNextTrieNodes()[ch],trieOperation);
        adjustTopSuggestionsAtNode(searchTerm, node.getTopSuggestions());
        return searchTerm;
    }

    private SearchTerm insertOrUpdateSearchTerm(SearchTerm searchTerm, Trie node,TrieOperation trieOperation){
        if (searchTerm.getId() == null || searchTerm.getId().isEmpty()){
            searchTerm = SearchTermUtility.createSearchTermWithWeightage(searchTerm.getWord(),defaultWeightage);
        }
        else{
            SearchTermUtility.incrementSearchTermWeightage(searchTerm,defaultWeightage);
        }
        insertSearchTermAtNode(searchTerm,node);
        return searchTerm ;
    }
    public SearchTerm searchForTerm (SearchTerm searchTerm){
        return traverseTrieForSearchTerm(searchTerm,0,trie,TrieOperation.UPSERT);
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

    private void insertSearchTermAtNode(SearchTerm searchTerm, Trie node){
        node.setStateOfTrie(TrieState.WORD_ENDS);
        node.setSearchTerm(searchTerm);
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

}
