package com.saadkasu.type_ahead_search_service.Stratergies;

import com.saadkasu.type_ahead_search_service.Models.SearchTerm;

import java.util.HashMap;
import java.util.List;

public interface IDecayStratergy {
    public List<SearchTerm> performDecay(List<SearchTerm> allSearchTerms);
}
