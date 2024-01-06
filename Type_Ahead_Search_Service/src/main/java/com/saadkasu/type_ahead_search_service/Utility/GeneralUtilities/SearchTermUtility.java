package com.saadkasu.type_ahead_search_service.Utility.GeneralUtilities;

import com.saadkasu.type_ahead_search_service.DTOs.SearchRequestDTO;
import com.saadkasu.type_ahead_search_service.DTOs.SearchResponseDTO;
import com.saadkasu.type_ahead_search_service.Models.SearchTerm;

import javax.swing.text.html.Option;
import java.util.*;

public class SearchTermUtility {
    public static SearchResponseDTO convertSingleSearchTermToDTO(Optional<SearchTerm> optionalSearchTerm){
        SearchResponseDTO responseDTO = new SearchResponseDTO();
        if (optionalSearchTerm.isEmpty()){
            responseDTO.setErrorMessage("There was an error. Please try again later");
            return responseDTO;
        }
        SearchTerm searchTerm = optionalSearchTerm.get();
        responseDTO.setWeightage(searchTerm.getWeightage());
        responseDTO.setWord(searchTerm.getWord());
        return responseDTO;
    }
    public static SearchTerm convertSingleDtoToSearchTerm (SearchRequestDTO requestDTO){
        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setWeightage(requestDTO.getWeightage());
        searchTerm.setWord(requestDTO.getWord());
        return searchTerm;
    }

    public static List<SearchResponseDTO> convertListOfSearchTermsToDTOs(Optional<List<SearchTerm>> optionalSearchTerms){
        List<SearchResponseDTO> responseDTOs = new ArrayList<>();
        if (optionalSearchTerms.isEmpty()){
            SearchResponseDTO responseDTO = new SearchResponseDTO();
            responseDTO.setErrorMessage("Could not return the product. Please try Later");
            responseDTOs.add(responseDTO);
            return responseDTOs;
        }
        List<SearchTerm> products = optionalSearchTerms.get();
        for (SearchTerm searchTerm : products){
            responseDTOs.add(convertSingleSearchTermToDTO(Optional.of(searchTerm)));
        }
        return responseDTOs;
    }

    public static String convertSearchTermToWord(SearchTerm searchTerm){
        return searchTerm.getWord();
    }

    public static SearchTerm convertStringToSearchTerm(String searchWord){
        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setWord(searchWord);
        return searchTerm;
    }
}
