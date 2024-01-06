package com.saadkasu.type_ahead_search_service.Stratergies;

import com.saadkasu.type_ahead_search_service.Configurations.CustomMetadatas.DecayFactorMetadata;
import com.saadkasu.type_ahead_search_service.Models.SearchTerm;

import java.util.List;

public class DecayByFactor implements IDecayStratergy{
    private double decayFactor;

    public DecayByFactor(){
        decayFactor =  DecayFactorMetadata.decayFactor;
    }
    @Override
    public List<SearchTerm> performDecay(List<SearchTerm> allSearchTerms) {
        for (SearchTerm searchTerm : allSearchTerms){
            searchTerm.setWeightage(searchTerm.getWeightage()*(1-decayFactor));
        }
        return allSearchTerms;
    }
}
