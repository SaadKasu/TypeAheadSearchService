package com.saadkasu.type_ahead_search_service.Repositories;

import com.saadkasu.type_ahead_search_service.Models.SearchTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISearchTermRepository extends JpaRepository<SearchTerm,Long> {
    Optional<SearchTerm> searchByWord(String word);
}
