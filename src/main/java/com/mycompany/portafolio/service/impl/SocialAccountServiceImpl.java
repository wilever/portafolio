package com.mycompany.portafolio.service.impl;

import com.mycompany.portafolio.service.SocialAccountService;
import com.mycompany.portafolio.domain.SocialAccount;
import com.mycompany.portafolio.repository.SocialAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SocialAccount.
 */
@Service
@Transactional
public class SocialAccountServiceImpl implements SocialAccountService {

    private final Logger log = LoggerFactory.getLogger(SocialAccountServiceImpl.class);

    private final SocialAccountRepository socialAccountRepository;

    public SocialAccountServiceImpl(SocialAccountRepository socialAccountRepository) {
        this.socialAccountRepository = socialAccountRepository;
    }

    /**
     * Save a socialAccount.
     *
     * @param socialAccount the entity to save
     * @return the persisted entity
     */
    @Override
    public SocialAccount save(SocialAccount socialAccount) {
        log.debug("Request to save SocialAccount : {}", socialAccount);
        return socialAccountRepository.save(socialAccount);
    }

    /**
     * Get all the socialAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SocialAccount> findAll(Pageable pageable) {
        log.debug("Request to get all SocialAccounts");
        return socialAccountRepository.findAll(pageable);
    }


    /**
     * Get one socialAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SocialAccount> findOne(Long id) {
        log.debug("Request to get SocialAccount : {}", id);
        return socialAccountRepository.findById(id);
    }

    /**
     * Delete the socialAccount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialAccount : {}", id);        socialAccountRepository.deleteById(id);
    }
}
