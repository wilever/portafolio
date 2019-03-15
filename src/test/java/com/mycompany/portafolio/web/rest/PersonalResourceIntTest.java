package com.mycompany.portafolio.web.rest;

import com.mycompany.portafolio.PortafolioApp;

import com.mycompany.portafolio.domain.Personal;
import com.mycompany.portafolio.domain.User;
import com.mycompany.portafolio.repository.PersonalRepository;
import com.mycompany.portafolio.service.PersonalService;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.portafolio.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonalResource REST controller.
 *
 * @see PersonalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortafolioApp.class)
public class PersonalResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private PersonalService personalService;

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

    private MockMvc restPersonalMockMvc;

    private Personal personal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonalResource personalResource = new PersonalResource(personalService);
        this.restPersonalMockMvc = MockMvcBuilders.standaloneSetup(personalResource)
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
    public static Personal createEntity(EntityManager em) {
        Personal personal = new Personal()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .profession(DEFAULT_PROFESSION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        personal.setUser(user);
        return personal;
    }

    @Before
    public void initTest() {
        personal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonal() throws Exception {
        int databaseSizeBeforeCreate = personalRepository.findAll().size();

        // Create the Personal
        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isCreated());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeCreate + 1);
        Personal testPersonal = personalList.get(personalList.size() - 1);
        assertThat(testPersonal.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPersonal.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPersonal.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testPersonal.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPersonal.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPersonalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalRepository.findAll().size();

        // Create the Personal with an existing ID
        personal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setFirstName(null);

        // Create the Personal, which fails.

        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setLastName(null);

        // Create the Personal, which fails.

        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProfessionIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalRepository.findAll().size();
        // set the field null
        personal.setProfession(null);

        // Create the Personal, which fails.

        restPersonalMockMvc.perform(post("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonals() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get all the personalList
        restPersonalMockMvc.perform(get("/api/personals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personal.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getPersonal() throws Exception {
        // Initialize the database
        personalRepository.saveAndFlush(personal);

        // Get the personal
        restPersonalMockMvc.perform(get("/api/personals/{id}", personal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personal.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingPersonal() throws Exception {
        // Get the personal
        restPersonalMockMvc.perform(get("/api/personals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonal() throws Exception {
        // Initialize the database
        personalService.save(personal);

        int databaseSizeBeforeUpdate = personalRepository.findAll().size();

        // Update the personal
        Personal updatedPersonal = personalRepository.findById(personal.getId()).get();
        // Disconnect from session so that the updates on updatedPersonal are not directly saved in db
        em.detach(updatedPersonal);
        updatedPersonal
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .profession(UPDATED_PROFESSION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPersonalMockMvc.perform(put("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonal)))
            .andExpect(status().isOk());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeUpdate);
        Personal testPersonal = personalList.get(personalList.size() - 1);
        assertThat(testPersonal.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPersonal.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonal.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testPersonal.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPersonal.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonal() throws Exception {
        int databaseSizeBeforeUpdate = personalRepository.findAll().size();

        // Create the Personal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalMockMvc.perform(put("/api/personals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal)))
            .andExpect(status().isBadRequest());

        // Validate the Personal in the database
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonal() throws Exception {
        // Initialize the database
        personalService.save(personal);

        int databaseSizeBeforeDelete = personalRepository.findAll().size();

        // Delete the personal
        restPersonalMockMvc.perform(delete("/api/personals/{id}", personal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Personal> personalList = personalRepository.findAll();
        assertThat(personalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personal.class);
        Personal personal1 = new Personal();
        personal1.setId(1L);
        Personal personal2 = new Personal();
        personal2.setId(personal1.getId());
        assertThat(personal1).isEqualTo(personal2);
        personal2.setId(2L);
        assertThat(personal1).isNotEqualTo(personal2);
        personal1.setId(null);
        assertThat(personal1).isNotEqualTo(personal2);
    }
}
