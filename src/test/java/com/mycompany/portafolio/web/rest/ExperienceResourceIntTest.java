package com.mycompany.portafolio.web.rest;

import com.mycompany.portafolio.PortafolioApp;

import com.mycompany.portafolio.domain.Experience;
import com.mycompany.portafolio.domain.Personal;
import com.mycompany.portafolio.repository.ExperienceRepository;
import com.mycompany.portafolio.service.ExperienceService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.mycompany.portafolio.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExperienceResource REST controller.
 *
 * @see ExperienceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortafolioApp.class)
public class ExperienceResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceService experienceService;

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

    private MockMvc restExperienceMockMvc;

    private Experience experience;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperienceResource experienceResource = new ExperienceResource(experienceService);
        this.restExperienceMockMvc = MockMvcBuilders.standaloneSetup(experienceResource)
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
    public static Experience createEntity(EntityManager em) {
        Experience experience = new Experience()
            .title(DEFAULT_TITLE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .isActive(DEFAULT_IS_ACTIVE)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Personal personal = PersonalResourceIntTest.createEntity(em);
        em.persist(personal);
        em.flush();
        experience.setPersonal(personal);
        return experience;
    }

    @Before
    public void initTest() {
        experience = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperience() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();

        // Create the Experience
        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isCreated());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate + 1);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExperience.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testExperience.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();

        // Create the Experience with an existing ID
        experience.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceRepository.findAll().size();
        // set the field null
        experience.setTitle(null);

        // Create the Experience, which fails.

        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isBadRequest());

        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceRepository.findAll().size();
        // set the field null
        experience.setStartDate(null);

        // Create the Experience, which fails.

        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isBadRequest());

        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceRepository.findAll().size();
        // set the field null
        experience.setEndDate(null);

        // Create the Experience, which fails.

        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isBadRequest());

        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceRepository.findAll().size();
        // set the field null
        experience.setIsActive(null);

        // Create the Experience, which fails.

        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isBadRequest());

        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceRepository.findAll().size();
        // set the field null
        experience.setDescription(null);

        // Create the Experience, which fails.

        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isBadRequest());

        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExperiences() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList
        restExperienceMockMvc.perform(get("/api/experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experience.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", experience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experience.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExperience() throws Exception {
        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperience() throws Exception {
        // Initialize the database
        experienceService.save(experience);

        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Update the experience
        Experience updatedExperience = experienceRepository.findById(experience.getId()).get();
        // Disconnect from session so that the updates on updatedExperience are not directly saved in db
        em.detach(updatedExperience);
        updatedExperience
            .title(UPDATED_TITLE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .description(UPDATED_DESCRIPTION);

        restExperienceMockMvc.perform(put("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExperience)))
            .andExpect(status().isOk());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExperience.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testExperience.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Create the Experience

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceMockMvc.perform(put("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experience)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExperience() throws Exception {
        // Initialize the database
        experienceService.save(experience);

        int databaseSizeBeforeDelete = experienceRepository.findAll().size();

        // Delete the experience
        restExperienceMockMvc.perform(delete("/api/experiences/{id}", experience.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Experience.class);
        Experience experience1 = new Experience();
        experience1.setId(1L);
        Experience experience2 = new Experience();
        experience2.setId(experience1.getId());
        assertThat(experience1).isEqualTo(experience2);
        experience2.setId(2L);
        assertThat(experience1).isNotEqualTo(experience2);
        experience1.setId(null);
        assertThat(experience1).isNotEqualTo(experience2);
    }
}
