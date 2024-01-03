package com.saadkasu.type_ahead_search_service.Stratergies;

import com.saadkasu.type_ahead_search_service.CustomMetadatas.DecayFactorMetadata;

public class DecayByFactor implements IDecayStratergy{
    private double decayFactor;

    DecayByFactor(){
        decayFactor =  DecayFactorMetadata.decayFactor;
    }
    @Override
    public void performDecay() {

    }
}
