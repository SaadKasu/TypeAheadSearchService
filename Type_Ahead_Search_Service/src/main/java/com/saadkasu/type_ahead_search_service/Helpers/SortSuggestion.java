package com.saadkasu.type_ahead_search_service.Helpers;

import com.saadkasu.type_ahead_search_service.Models.SearchTerm;

import java.util.Comparator;

public class SortSuggestion implements ISortHelper {
    public int compare(SearchTerm a, SearchTerm b){
        if (a.getWeightage() == b.getWeightage())
            return a.getWord().compareTo(b.getWord());
        else if (b.getWeightage() > a.getWeightage())
            return 1;
        else
            return -1;
    }
}
