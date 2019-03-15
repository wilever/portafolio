package com.mycompany.portafolio.web.rest;

import com.mycompany.portafolio.PortafolioApp;

import com.mycompany.portafolio.domain.Languague;
import com.mycompany.portafolio.domain.Personal;
import com.mycompany.portafolio.repository.LanguagueRepository;
import com.mycompany.portafolio.service.LanguagueService;
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

import com.mycompany.portafolio.domain.enumeration.LanguagueLevel;
/**
 * Test class for the LanguagueResource REST controller.
 *
 * @see LanguagueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortafolioApp.class)
public class LanguagueResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final LanguagueLevel DEFAULT_LEVEL = LanguagueLevel.WRITE;
    private static final LanguagueLevel UPDATED_LEVEL = LanguagueLevel.WRITE_LISTENING;

    @Autowired
    private LanguagueRepository languagueRepository;

    @Autowired
    private LanguagueService languagueService;

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

    private MockMvc restLanguagueMockMvc;

    private Languague languague;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LanguagueResource languagueResource = new LanguagueResource(languagueService);
        this.restLanguagueMockMvc = MockMvcBuilders.standaloneSetup(languagueResource)
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
    public static Languague createEntity(EntityManager em) {
        Languague languague = new Languague()
            .name(DEFAULT_NAME)
            .isActive(DEFAULT_IS_ACTIVE)
            .level(DEFAULT_LEVEL);
        // Add required entity
        Personal personal = PersonalResourceIntTest.createEntity(em);
        em.persist(personal);
        em.flush();
        languague.setPersonal(personal);
        return languague;
    }

    @Before
    public void initTest() {
        languague = createEntity(em);
    }

    @Test
    @Transactional
    public void createLanguague() throws Exception {
        int databaseSizeBeforeCreate = languagueRepository.findAll().size();

        // Create the Languague
        restLanguagueMockMvc.perform(post("/api/languagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languague)))
            .andExpect(status().isCreated());

        // Validate the Languague in the database
        List<Languague> languagueList = languagueRepository.findAll();
        assertThat(languagueList).hasSize(databaseSizeBeforeCreate + 1);
        Languague testLanguague = languagueList.get(languagueList.size() - 1);
        assertThat(testLanguague.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLanguague.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testLanguague.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createLanguagueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = languagueRepository.findAll().size();

        // Create the Languague with an existing ID
        languague.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLanguagueMockMvc.perform(post("/api/languagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languague)))
            .andExpect(status().isBadRequest());

        // Validate the Languague in the database
        List<Languague> languagueList = languagueRepository.findAll();
        assertThat(languagueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = languagueRepository.findAll().size();
        // set the field null
        languague.setName(null);

        // Create the Languague, which fails.

        restLanguagueMockMvc.perform(post("/api/languagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languague)))
            .andExpect(status().isBadRequest());

        List<Languague> languagueList = languagueRepository.findAll();
        assertThat(languagueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = languagueRepository.findAll().size();
        // set the field null
        languague.setIsActive(null);

        // Create the Languague, which fails.

        restLanguagueMockMvc.perform(post("/api/languagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languague)))
            .andExpect(status().isBadRequest());

        List<Languague> languagueList = languagueRepository.findAll();
        assertThat(languagueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLanguagues() throws Exception {
        // Initialize the database
        languagueRepository.saveAndFlush(languague);

        // Get all the languagueList
        restLanguagueMockMvc.perform(get("/api/languagues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(languague.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getLanguague() throws Exception {
        // Initialize the database
        languagueRepository.saveAndFlush(languague);

        // Get the languague
        restLanguagueMockMvc.perform(get("/api/languagues/{id}", languague.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(languague.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLanguague() throws Exception {
        // Get the languague
        restLanguagueMockMvc.perform(get("/api/languagues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLanguague() throws Exception {
        // Initialize the database
        languagueService.save(languague);

        int databaseSizeBeforeUpdate = languagueRepository.findAll().size();

        // Update the languague
        Languague updatedLanguague = languagueRepository.findById(languague.getId()).get();
        // Disconnect from session so that the updates on updatedLanguague are not directly saved in db
        em.detach(updatedLanguague);
        updatedLanguague
            .name(UPDATED_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .level(UPDATED_LEVEL);

        restLanguagueMockMvc.perform(put("/api/languagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLanguague)))
            .andExpect(status().isOk());

        // Validate the Languague in the database
        List<Languague> languagueList = languagueRepository.findAll();
        assertThat(languagueList).hasSize(databaseSizeBeforeUpdate);
        Languague testLanguague = languagueList.get(languagueList.size() - 1);
        assertThat(testLanguague.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLanguague.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testLanguague.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingLanguague() throws Exception {
        int databaseSizeBeforeUpdate = languagueRepository.findAll().size();

        // Create the Languague

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLanguagueMockMvc.perform(put("/api/languagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languague)))
            .andExpect(status().isBadRequest());

        // Validate the Languague in the database
        List<Languague> languagueList = languagueRepository.findAll();
        assertThat(languagueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLanguague() throws Exception {
        // Initialize the database
        languagueService.save(languague);

        int databaseSizeBeforeDelete = languagueRepository.findAll().size();

        // Delete the languague
        restLanguagueMockMvc.perform(delete("/api/languagues/{id}", languague.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Languague> languagueList = languagueRepository.findAll();
        assertThat(languagueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Languague.class);
        Languague languague1 = new Languague();
        languague1.setId(1L);
        Languague languague2 = new Languague();
        languague2.setId(languague1.getId());
        assertThat(languague1).isEqualTo(languague2);
        languague2.setId(2L);
        assertThat(languague1).isNotEqualTo(languague2);
        languague1.setId(null);
        assertThat(languague1).isNotEqualTo(languague2);
    }
}
