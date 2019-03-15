package com.mycompany.portafolio.web.rest;

import com.mycompany.portafolio.PortafolioApp;

import com.mycompany.portafolio.domain.AboutMe;
import com.mycompany.portafolio.domain.Personal;
import com.mycompany.portafolio.repository.AboutMeRepository;
import com.mycompany.portafolio.service.AboutMeService;
import com.mycompany.portafolio.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.portafolio.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AboutMeResource REST controller.
 *
 * @see AboutMeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortafolioApp.class)
public class AboutMeResourceIntTest {

    private static final String DEFAULT_RESUME = "AAAAAAAAAA";
    private static final String UPDATED_RESUME = "BBBBBBBBBB";

    @Autowired
    private AboutMeRepository aboutMeRepository;

    @Autowired
    private AboutMeService aboutMeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAboutMeMockMvc;

    private AboutMe aboutMe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AboutMeResource aboutMeResource = new AboutMeResource(aboutMeService);
        this.restAboutMeMockMvc = MockMvcBuilders.standaloneSetup(aboutMeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AboutMe createEntity(EntityManager em) {
        AboutMe aboutMe = new AboutMe()
            .resume(DEFAULT_RESUME);
        // Add required entity
        Personal personal = PersonalResourceIntTest.createEntity(em);
        em.persist(personal);
        em.flush();
        aboutMe.setPersonal(personal);
        return aboutMe;
    }

    @Before
    public void initTest() {
        aboutMe = createEntity(em);
    }

    @Test
    @Transactional
    public void createAboutMe() throws Exception {
        int databaseSizeBeforeCreate = aboutMeRepository.findAll().size();

        // Create the AboutMe
        restAboutMeMockMvc.perform(post("/api/about-mes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aboutMe)))
            .andExpect(status().isCreated());

        // Validate the AboutMe in the database
        List<AboutMe> aboutMeList = aboutMeRepository.findAll();
        assertThat(aboutMeList).hasSize(databaseSizeBeforeCreate + 1);
        AboutMe testAboutMe = aboutMeList.get(aboutMeList.size() - 1);
        assertThat(testAboutMe.getResume()).isEqualTo(DEFAULT_RESUME);
    }

    @Test
    @Transactional
    public void createAboutMeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aboutMeRepository.findAll().size();

        // Create the AboutMe with an existing ID
        aboutMe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAboutMeMockMvc.perform(post("/api/about-mes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aboutMe)))
            .andExpect(status().isBadRequest());

        // Validate the AboutMe in the database
        List<AboutMe> aboutMeList = aboutMeRepository.findAll();
        assertThat(aboutMeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkResumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aboutMeRepository.findAll().size();
        // set the field null
        aboutMe.setResume(null);

        // Create the AboutMe, which fails.

        restAboutMeMockMvc.perform(post("/api/about-mes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aboutMe)))
            .andExpect(status().isBadRequest());

        List<AboutMe> aboutMeList = aboutMeRepository.findAll();
        assertThat(aboutMeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAboutMes() throws Exception {
        // Initialize the database
        aboutMeRepository.saveAndFlush(aboutMe);

        // Get all the aboutMeList
        restAboutMeMockMvc.perform(get("/api/about-mes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aboutMe.getId().intValue())))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME.toString())));
    }
    
    @Test
    @Transactional
    public void getAboutMe() throws Exception {
        // Initialize the database
        aboutMeRepository.saveAndFlush(aboutMe);

        // Get the aboutMe
        restAboutMeMockMvc.perform(get("/api/about-mes/{id}", aboutMe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aboutMe.getId().intValue()))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAboutMe() throws Exception {
        // Get the aboutMe
        restAboutMeMockMvc.perform(get("/api/about-mes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAboutMe() throws Exception {
        // Initialize the database
        aboutMeService.save(aboutMe);

        int databaseSizeBeforeUpdate = aboutMeRepository.findAll().size();

        // Update the aboutMe
        AboutMe updatedAboutMe = aboutMeRepository.findById(aboutMe.getId()).get();
        // Disconnect from session so that the updates on updatedAboutMe are not directly saved in db
        em.detach(updatedAboutMe);
        updatedAboutMe
            .resume(UPDATED_RESUME);

        restAboutMeMockMvc.perform(put("/api/about-mes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAboutMe)))
            .andExpect(status().isOk());

        // Validate the AboutMe in the database
        List<AboutMe> aboutMeList = aboutMeRepository.findAll();
        assertThat(aboutMeList).hasSize(databaseSizeBeforeUpdate);
        AboutMe testAboutMe = aboutMeList.get(aboutMeList.size() - 1);
        assertThat(testAboutMe.getResume()).isEqualTo(UPDATED_RESUME);
    }

    @Test
    @Transactional
    public void updateNonExistingAboutMe() throws Exception {
        int databaseSizeBeforeUpdate = aboutMeRepository.findAll().size();

        // Create the AboutMe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAboutMeMockMvc.perform(put("/api/about-mes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aboutMe)))
            .andExpect(status().isBadRequest());

        // Validate the AboutMe in the database
        List<AboutMe> aboutMeList = aboutMeRepository.findAll();
        assertThat(aboutMeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAboutMe() throws Exception {
        // Initialize the database
        aboutMeService.save(aboutMe);

        int databaseSizeBeforeDelete = aboutMeRepository.findAll().size();

        // Delete the aboutMe
        restAboutMeMockMvc.perform(delete("/api/about-mes/{id}", aboutMe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AboutMe> aboutMeList = aboutMeRepository.findAll();
        assertThat(aboutMeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AboutMe.class);
        AboutMe aboutMe1 = new AboutMe();
        aboutMe1.setId(1L);
        AboutMe aboutMe2 = new AboutMe();
        aboutMe2.setId(aboutMe1.getId());
        assertThat(aboutMe1).isEqualTo(aboutMe2);
        aboutMe2.setId(2L);
        assertThat(aboutMe1).isNotEqualTo(aboutMe2);
        aboutMe1.setId(null);
        assertThat(aboutMe1).isNotEqualTo(aboutMe2);
    }
}
