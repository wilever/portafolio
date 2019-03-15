package com.mycompany.portafolio.service.impl;

import com.mycompany.portafolio.service.LanguagueService;
import com.mycompany.portafolio.domain.Languague;
import com.mycompany.portafolio.repository.LanguagueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Languague.
 */
@Service
@Transactional
public class LanguagueServiceImpl implements LanguagueService {

    private final Logger log = LoggerFactory.getLogger(LanguagueServiceImpl.class);

    private final LanguagueRepository languagueRepository;

    public LanguagueServiceImpl(LanguagueRepository languagueRepository) {
        this.languagueRepository = languagueRepository;
    }

    /**
     * Save a languague.
     *
     * @param languague the entity to save
     * @return the persisted entity
     */
    @Override
    public Languague save(Languague languague) {
        log.debug("Request to save Languague : {}", languague);
        return languagueRepository.save(languague);
    }

    /**
     * Get all the languagues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Languague> findAll(Pageable pageable) {
        log.debug("Request to get all Languagues");
        return languagueRepository.findAll(pageable);
    }


    /**
     * Get one languague by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Languague> findOne(Long id) {
        log.debug("Request to get Languague : {}", id);
        return languagueRepository.findById(id);
    }

    /**
     * Delete the languague by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Languague : {}", id);        languagueRepository.deleteById(id);
    }
}
