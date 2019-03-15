package com.mycompany.portafolio.service;

import com.mycompany.portafolio.domain.SocialAccount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SocialAccount.
 */
public interface SocialAccountService {

    /**
     * Save a socialAccount.
     *
     * @param socialAccount the entity to save
     * @return the persisted entity
     */
    SocialAccount save(SocialAccount socialAccount);

    /**
     * Get all the socialAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SocialAccount> findAll(Pageable pageable);


    /**
     * Get the "id" socialAccount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SocialAccount> findOne(Long id);

    /**
     * Delete the "id" socialAccount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
