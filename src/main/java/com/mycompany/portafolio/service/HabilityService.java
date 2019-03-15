package com.mycompany.portafolio.service;

import com.mycompany.portafolio.domain.Hability;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Hability.
 */
public interface HabilityService {

    /**
     * Save a hability.
     *
     * @param hability the entity to save
     * @return the persisted entity
     */
    Hability save(Hability hability);

    /**
     * Get all the habilities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Hability> findAll(Pageable pageable);


    /**
     * Get the "id" hability.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Hability> findOne(Long id);

    /**
     * Delete the "id" hability.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
