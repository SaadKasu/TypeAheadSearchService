package com.saadkasu.type_ahead_search_service.Utility.Trie.Models;

import com.saadkasu.type_ahead_search_service.Models.SearchTerm;
import lombok.Data;

import java.util.ArrayList;
@Data
public class Trie {
    private ArrayList<SearchTerm> topSuggestions;
    private Trie[] nextTrieNodes;
    private TrieState stateOfTrie;
    private SearchTerm searchTerm;
    public Trie(){
        topSuggestions = new ArrayList<>();
        nextTrieNodes = new Trie[256];
        stateOfTrie = TrieState.WORD_DOES_NOT_END;
    }
}
