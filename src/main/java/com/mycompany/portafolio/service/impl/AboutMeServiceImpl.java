package com.mycompany.portafolio.service.impl;

import com.mycompany.portafolio.service.AboutMeService;
import com.mycompany.portafolio.domain.AboutMe;
import com.mycompany.portafolio.repository.AboutMeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing AboutMe.
 */
@Service
@Transactional
public class AboutMeServiceImpl implements AboutMeService {

    private final Logger log = LoggerFactory.getLogger(AboutMeServiceImpl.class);

    private final AboutMeRepository aboutMeRepository;

    public AboutMeServiceImpl(AboutMeRepository aboutMeRepository) {
        this.aboutMeRepository = aboutMeRepository;
    }

    /**
     * Save a aboutMe.
     *
     * @param aboutMe the entity to save
     * @return the persisted entity
     */
    @Override
    public AboutMe save(AboutMe aboutMe) {
        log.debug("Request to save AboutMe : {}", aboutMe);
        return aboutMeRepository.save(aboutMe);
    }

    /**
     * Get all the aboutMes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AboutMe> findAll(Pageable pageable) {
        log.debug("Request to get all AboutMes");
        return aboutMeRepository.findAll(pageable);
    }


    /**
     * Get one aboutMe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AboutMe> findOne(Long id) {
        log.debug("Request to get AboutMe : {}", id);
        return aboutMeRepository.findById(id);
    }

    /**
     * Delete the aboutMe by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AboutMe : {}", id);        aboutMeRepository.deleteById(id);
    }
}
