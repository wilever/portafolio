package com.mycompany.portafolio.web.rest;
import com.mycompany.portafolio.domain.Languague;
import com.mycompany.portafolio.service.LanguagueService;
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
 * REST controller for managing Languague.
 */
@RestController
@RequestMapping("/api")
public class LanguagueResource {

    private final Logger log = LoggerFactory.getLogger(LanguagueResource.class);

    private static final String ENTITY_NAME = "languague";

    private final LanguagueService languagueService;

    public LanguagueResource(LanguagueService languagueService) {
        this.languagueService = languagueService;
    }

    /**
     * POST  /languagues : Create a new languague.
     *
     * @param languague the languague to create
     * @return the ResponseEntity with status 201 (Created) and with body the new languague, or with status 400 (Bad Request) if the languague has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/languagues")
    public ResponseEntity<Languague> createLanguague(@Valid @RequestBody Languague languague) throws URISyntaxException {
        log.debug("REST request to save Languague : {}", languague);
        if (languague.getId() != null) {
            throw new BadRequestAlertException("A new languague cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Languague result = languagueService.save(languague);
        return ResponseEntity.created(new URI("/api/languagues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /languagues : Updates an existing languague.
     *
     * @param languague the languague to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated languague,
     * or with status 400 (Bad Request) if the languague is not valid,
     * or with status 500 (Internal Server Error) if the languague couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/languagues")
    public ResponseEntity<Languague> updateLanguague(@Valid @RequestBody Languague languague) throws URISyntaxException {
        log.debug("REST request to update Languague : {}", languague);
        if (languague.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Languague result = languagueService.save(languague);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, languague.getId().toString()))
            .body(result);
    }

    /**
     * GET  /languagues : get all the languagues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of languagues in body
     */
    @GetMapping("/languagues")
    public ResponseEntity<List<Languague>> getAllLanguagues(Pageable pageable) {
        log.debug("REST request to get a page of Languagues");
        Page<Languague> page = languagueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/languagues");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /languagues/:id : get the "id" languague.
     *
     * @param id the id of the languague to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the languague, or with status 404 (Not Found)
     */
    @GetMapping("/languagues/{id}")
    public ResponseEntity<Languague> getLanguague(@PathVariable Long id) {
        log.debug("REST request to get Languague : {}", id);
        Optional<Languague> languague = languagueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(languague);
    }

    /**
     * DELETE  /languagues/:id : delete the "id" languague.
     *
     * @param id the id of the languague to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/languagues/{id}")
    public ResponseEntity<Void> deleteLanguague(@PathVariable Long id) {
        log.debug("REST request to delete Languague : {}", id);
        languagueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
