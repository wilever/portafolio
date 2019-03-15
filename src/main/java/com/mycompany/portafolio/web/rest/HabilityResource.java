package com.mycompany.portafolio.web.rest;
import com.mycompany.portafolio.domain.Hability;
import com.mycompany.portafolio.service.HabilityService;
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
 * REST controller for managing Hability.
 */
@RestController
@RequestMapping("/api")
public class HabilityResource {

    private final Logger log = LoggerFactory.getLogger(HabilityResource.class);

    private static final String ENTITY_NAME = "hability";

    private final HabilityService habilityService;

    public HabilityResource(HabilityService habilityService) {
        this.habilityService = habilityService;
    }

    /**
     * POST  /habilities : Create a new hability.
     *
     * @param hability the hability to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hability, or with status 400 (Bad Request) if the hability has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/habilities")
    public ResponseEntity<Hability> createHability(@Valid @RequestBody Hability hability) throws URISyntaxException {
        log.debug("REST request to save Hability : {}", hability);
        if (hability.getId() != null) {
            throw new BadRequestAlertException("A new hability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hability result = habilityService.save(hability);
        return ResponseEntity.created(new URI("/api/habilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /habilities : Updates an existing hability.
     *
     * @param hability the hability to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hability,
     * or with status 400 (Bad Request) if the hability is not valid,
     * or with status 500 (Internal Server Error) if the hability couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/habilities")
    public ResponseEntity<Hability> updateHability(@Valid @RequestBody Hability hability) throws URISyntaxException {
        log.debug("REST request to update Hability : {}", hability);
        if (hability.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hability result = habilityService.save(hability);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hability.getId().toString()))
            .body(result);
    }

    /**
     * GET  /habilities : get all the habilities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of habilities in body
     */
    @GetMapping("/habilities")
    public ResponseEntity<List<Hability>> getAllHabilities(Pageable pageable) {
        log.debug("REST request to get a page of Habilities");
        Page<Hability> page = habilityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/habilities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /habilities/:id : get the "id" hability.
     *
     * @param id the id of the hability to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hability, or with status 404 (Not Found)
     */
    @GetMapping("/habilities/{id}")
    public ResponseEntity<Hability> getHability(@PathVariable Long id) {
        log.debug("REST request to get Hability : {}", id);
        Optional<Hability> hability = habilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hability);
    }

    /**
     * DELETE  /habilities/:id : delete the "id" hability.
     *
     * @param id the id of the hability to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/habilities/{id}")
    public ResponseEntity<Void> deleteHability(@PathVariable Long id) {
        log.debug("REST request to delete Hability : {}", id);
        habilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
