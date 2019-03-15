package com.mycompany.portafolio.service.impl;

import com.mycompany.portafolio.service.FormationService;
import com.mycompany.portafolio.domain.Formation;
import com.mycompany.portafolio.repository.FormationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Formation.
 */
@Service
@Transactional
public class FormationServiceImpl implements FormationService {

    private final Logger log = LoggerFactory.getLogger(FormationServiceImpl.class);

    private final FormationRepository formationRepository;

    public FormationServiceImpl(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    /**
     * Save a formation.
     *
     * @param formation the entity to save
     * @return the persisted entity
     */
    @Override
    public Formation save(Formation formation) {
        log.debug("Request to save Formation : {}", formation);
        return formationRepository.save(formation);
    }

    /**
     * Get all the formations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Formation> findAll(Pageable pageable) {
        log.debug("Request to get all Formations");
        return formationRepository.findAll(pageable);
    }


    /**
     * Get one formation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Formation> findOne(Long id) {
        log.debug("Request to get Formation : {}", id);
        return formationRepository.findById(id);
    }

    /**
     * Delete the formation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Formation : {}", id);        formationRepository.deleteById(id);
    }
}
