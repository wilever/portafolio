package com.mycompany.portafolio.service;

import com.mycompany.portafolio.domain.Languague;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Languague.
 */
public interface LanguagueService {

    /**
     * Save a languague.
     *
     * @param languague the entity to save
     * @return the persisted entity
     */
    Languague save(Languague languague);

    /**
     * Get all the languagues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Languague> findAll(Pageable pageable);


    /**
     * Get the "id" languague.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Languague> findOne(Long id);

    /**
     * Delete the "id" languague.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
