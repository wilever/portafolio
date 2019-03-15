package com.mycompany.portafolio.web.rest;
import com.mycompany.portafolio.domain.AboutMe;
import com.mycompany.portafolio.service.AboutMeService;
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
 * REST controller for managing AboutMe.
 */
@RestController
@RequestMapping("/api")
public class AboutMeResource {

    private final Logger log = LoggerFactory.getLogger(AboutMeResource.class);

    private static final String ENTITY_NAME = "aboutMe";

    private final AboutMeService aboutMeService;

    public AboutMeResource(AboutMeService aboutMeService) {
        this.aboutMeService = aboutMeService;
    }

    /**
     * POST  /about-mes : Create a new aboutMe.
     *
     * @param aboutMe the aboutMe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aboutMe, or with status 400 (Bad Request) if the aboutMe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/about-mes")
    public ResponseEntity<AboutMe> createAboutMe(@Valid @RequestBody AboutMe aboutMe) throws URISyntaxException {
        log.debug("REST request to save AboutMe : {}", aboutMe);
        if (aboutMe.getId() != null) {
            throw new BadRequestAlertException("A new aboutMe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AboutMe result = aboutMeService.save(aboutMe);
        return ResponseEntity.created(new URI("/api/about-mes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /about-mes : Updates an existing aboutMe.
     *
     * @param aboutMe the aboutMe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aboutMe,
     * or with status 400 (Bad Request) if the aboutMe is not valid,
     * or with status 500 (Internal Server Error) if the aboutMe couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/about-mes")
    public ResponseEntity<AboutMe> updateAboutMe(@Valid @RequestBody AboutMe aboutMe) throws URISyntaxException {
        log.debug("REST request to update AboutMe : {}", aboutMe);
        if (aboutMe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AboutMe result = aboutMeService.save(aboutMe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aboutMe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /about-mes : get all the aboutMes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of aboutMes in body
     */
    @GetMapping("/about-mes")
    public ResponseEntity<List<AboutMe>> getAllAboutMes(Pageable pageable) {
        log.debug("REST request to get a page of AboutMes");
        Page<AboutMe> page = aboutMeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/about-mes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /about-mes/:id : get the "id" aboutMe.
     *
     * @param id the id of the aboutMe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aboutMe, or with status 404 (Not Found)
     */
    @GetMapping("/about-mes/{id}")
    public ResponseEntity<AboutMe> getAboutMe(@PathVariable Long id) {
        log.debug("REST request to get AboutMe : {}", id);
        Optional<AboutMe> aboutMe = aboutMeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aboutMe);
    }

    /**
     * DELETE  /about-mes/:id : delete the "id" aboutMe.
     *
     * @param id the id of the aboutMe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/about-mes/{id}")
    public ResponseEntity<Void> deleteAboutMe(@PathVariable Long id) {
        log.debug("REST request to delete AboutMe : {}", id);
        aboutMeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
