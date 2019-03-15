package com.mycompany.portafolio.web.rest;
import com.mycompany.portafolio.domain.Hobby;
import com.mycompany.portafolio.service.HobbyService;
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
 * REST controller for managing Hobby.
 */
@RestController
@RequestMapping("/api")
public class HobbyResource {

    private final Logger log = LoggerFactory.getLogger(HobbyResource.class);

    private static final String ENTITY_NAME = "hobby";

    private final HobbyService hobbyService;

    public HobbyResource(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    /**
     * POST  /hobbies : Create a new hobby.
     *
     * @param hobby the hobby to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hobby, or with status 400 (Bad Request) if the hobby has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hobbies")
    public ResponseEntity<Hobby> createHobby(@Valid @RequestBody Hobby hobby) throws URISyntaxException {
        log.debug("REST request to save Hobby : {}", hobby);
        if (hobby.getId() != null) {
            throw new BadRequestAlertException("A new hobby cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hobby result = hobbyService.save(hobby);
        return ResponseEntity.created(new URI("/api/hobbies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hobbies : Updates an existing hobby.
     *
     * @param hobby the hobby to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hobby,
     * or with status 400 (Bad Request) if the hobby is not valid,
     * or with status 500 (Internal Server Error) if the hobby couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hobbies")
    public ResponseEntity<Hobby> updateHobby(@Valid @RequestBody Hobby hobby) throws URISyntaxException {
        log.debug("REST request to update Hobby : {}", hobby);
        if (hobby.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hobby result = hobbyService.save(hobby);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hobby.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hobbies : get all the hobbies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hobbies in body
     */
    @GetMapping("/hobbies")
    public ResponseEntity<List<Hobby>> getAllHobbies(Pageable pageable) {
        log.debug("REST request to get a page of Hobbies");
        Page<Hobby> page = hobbyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hobbies");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /hobbies/:id : get the "id" hobby.
     *
     * @param id the id of the hobby to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hobby, or with status 404 (Not Found)
     */
    @GetMapping("/hobbies/{id}")
    public ResponseEntity<Hobby> getHobby(@PathVariable Long id) {
        log.debug("REST request to get Hobby : {}", id);
        Optional<Hobby> hobby = hobbyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hobby);
    }

    /**
     * DELETE  /hobbies/:id : delete the "id" hobby.
     *
     * @param id the id of the hobby to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hobbies/{id}")
    public ResponseEntity<Void> deleteHobby(@PathVariable Long id) {
        log.debug("REST request to delete Hobby : {}", id);
        hobbyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
