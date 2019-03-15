package com.mycompany.portafolio.service;

import com.mycompany.portafolio.domain.Formation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Formation.
 */
public interface FormationService {

    /**
     * Save a formation.
     *
     * @param formation the entity to save
     * @return the persisted entity
     */
    Formation save(Formation formation);

    /**
     * Get all the formations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Formation> findAll(Pageable pageable);


    /**
     * Get the "id" formation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Formation> findOne(Long id);

    /**
     * Delete the "id" formation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
