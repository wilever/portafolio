package com.mycompany.portafolio.service.impl;

import com.mycompany.portafolio.service.HobbyService;
import com.mycompany.portafolio.domain.Hobby;
import com.mycompany.portafolio.repository.HobbyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Hobby.
 */
@Service
@Transactional
public class HobbyServiceImpl implements HobbyService {

    private final Logger log = LoggerFactory.getLogger(HobbyServiceImpl.class);

    private final HobbyRepository hobbyRepository;

    public HobbyServiceImpl(HobbyRepository hobbyRepository) {
        this.hobbyRepository = hobbyRepository;
    }

    /**
     * Save a hobby.
     *
     * @param hobby the entity to save
     * @return the persisted entity
     */
    @Override
    public Hobby save(Hobby hobby) {
        log.debug("Request to save Hobby : {}", hobby);
        return hobbyRepository.save(hobby);
    }

    /**
     * Get all the hobbies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Hobby> findAll(Pageable pageable) {
        log.debug("Request to get all Hobbies");
        return hobbyRepository.findAll(pageable);
    }


    /**
     * Get one hobby by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Hobby> findOne(Long id) {
        log.debug("Request to get Hobby : {}", id);
        return hobbyRepository.findById(id);
    }

    /**
     * Delete the hobby by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hobby : {}", id);        hobbyRepository.deleteById(id);
    }
}
