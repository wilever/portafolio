package com.mycompany.portafolio.web.rest;
import com.mycompany.portafolio.domain.SocialAccount;
import com.mycompany.portafolio.service.SocialAccountService;
import com.mycompany.portafolio.web.rest.errors.BadRequestAlertException;
import com.mycompany.portafolio.web.rest.util.HeaderUtil;
import com.mycompany.portafolio.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SocialAccount.
 */
@RestController
@RequestMapping("/api")
public class SocialAccountResource {

    private final Logger log = LoggerFactory.getLogger(SocialAccountResource.class);

    private static final String ENTITY_NAME = "socialAccount";

    private final SocialAccountService socialAccountService;

    public SocialAccountResource(SocialAccountService socialAccountService) {
        this.socialAccountService = socialAccountService;
    }

    /**
     * POST  /social-accounts : Create a new socialAccount.
     *
     * @param socialAccount the socialAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socialAccount, or with status 400 (Bad Request) if the socialAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/social-accounts")
    public ResponseEntity<SocialAccount> createSocialAccount(@Valid @RequestBody SocialAccount socialAccount) throws URISyntaxException {
        log.debug("REST request to save SocialAccount : {}", socialAccount);
        if (socialAccount.getId() != null) {
            throw new BadRequestAlertException("A new socialAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialAccount result = socialAccountService.save(socialAccount);
        return ResponseEntity.created(new URI("/api/social-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-accounts : Updates an existing socialAccount.
     *
     * @param socialAccount the socialAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socialAccount,
     * or with status 400 (Bad Request) if the socialAccount is not valid,
     * or with status 500 (Internal Server Error) if the socialAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/social-accounts")
    public ResponseEntity<SocialAccount> updateSocialAccount(@Valid @RequestBody SocialAccount socialAccount) throws URISyntaxException {
        log.debug("REST request to update SocialAccount : {}", socialAccount);
        if (socialAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SocialAccount result = socialAccountService.save(socialAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socialAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-accounts : get all the socialAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of socialAccounts in body
     */
    @GetMapping("/social-accounts")
    public ResponseEntity<List<SocialAccount>> getAllSocialAccounts(Pageable pageable) {
        log.debug("REST request to get a page of SocialAccounts");
        Page<SocialAccount> page = socialAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/social-accounts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /social-accounts/:id : get the "id" socialAccount.
     *
     * @param id the id of the socialAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socialAccount, or with status 404 (Not Found)
     */
    @GetMapping("/social-accounts/{id}")
    public ResponseEntity<SocialAccount> getSocialAccount(@PathVariable Long id) {
        log.debug("REST request to get SocialAccount : {}", id);
        Optional<SocialAccount> socialAccount = socialAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialAccount);
    }

    /**
     * DELETE  /social-accounts/:id : delete the "id" socialAccount.
     *
     * @param id the id of the socialAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/social-accounts/{id}")
    public ResponseEntity<Void> deleteSocialAccount(@PathVariable Long id) {
        log.debug("REST request to delete SocialAccount : {}", id);
        socialAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
