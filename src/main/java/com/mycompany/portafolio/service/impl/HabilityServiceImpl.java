package com.mycompany.portafolio.service.impl;

import com.mycompany.portafolio.service.HabilityService;
import com.mycompany.portafolio.domain.Hability;
import com.mycompany.portafolio.repository.HabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Hability.
 */
@Service
@Transactional
public class HabilityServiceImpl implements HabilityService {

    private final Logger log = LoggerFactory.getLogger(HabilityServiceImpl.class);

    private final HabilityRepository habilityRepository;

    public HabilityServiceImpl(HabilityRepository habilityRepository) {
        this.habilityRepository = habilityRepository;
    }

    /**
     * Save a hability.
     *
     * @param hability the entity to save
     * @return the persisted entity
     */
    @Override
    public Hability save(Hability hability) {
        log.debug("Request to save Hability : {}", hability);
        return habilityRepository.save(hability);
    }

    /**
     * Get all the habilities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Hability> findAll(Pageable pageable) {
        log.debug("Request to get all Habilities");
        return habilityRepository.findAll(pageable);
    }


    /**
     * Get one hability by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Hability> findOne(Long id) {
        log.debug("Request to get Hability : {}", id);
        return habilityRepository.findById(id);
    }

    /**
     * Delete the hability by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hability : {}", id);        habilityRepository.deleteById(id);
    }
}
