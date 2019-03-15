package com.mycompany.portafolio.service;

import com.mycompany.portafolio.domain.AboutMe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AboutMe.
 */
public interface AboutMeService {

    /**
     * Save a aboutMe.
     *
     * @param aboutMe the entity to save
     * @return the persisted entity
     */
    AboutMe save(AboutMe aboutMe);

    /**
     * Get all the aboutMes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AboutMe> findAll(Pageable pageable);


    /**
     * Get the "id" aboutMe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AboutMe> findOne(Long id);

    /**
     * Delete the "id" aboutMe.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
