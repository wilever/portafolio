package com.mycompany.portafolio.web.rest;

import com.mycompany.portafolio.PortafolioApp;

import com.mycompany.portafolio.domain.Hability;
import com.mycompany.portafolio.domain.Personal;
import com.mycompany.portafolio.repository.HabilityRepository;
import com.mycompany.portafolio.service.HabilityService;
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

import com.mycompany.portafolio.domain.enumeration.HabilityType;
/**
 * Test class for the HabilityResource REST controller.
 *
 * @see HabilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortafolioApp.class)
public class HabilityResourceIntTest {

    private static final HabilityType DEFAULT_TYPE = HabilityType.FRONTEND;
    private static final HabilityType UPDATED_TYPE = HabilityType.BACKEND;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    @Autowired
    private HabilityRepository habilityRepository;

    @Autowired
    private HabilityService habilityService;

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

    private MockMvc restHabilityMockMvc;

    private Hability hability;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HabilityResource habilityResource = new HabilityResource(habilityService);
        this.restHabilityMockMvc = MockMvcBuilders.standaloneSetup(habilityResource)
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
    public static Hability createEntity(EntityManager em) {
        Hability hability = new Hability()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE);
        // Add required entity
        Personal personal = PersonalResourceIntTest.createEntity(em);
        em.persist(personal);
        em.flush();
        hability.setPersonal(personal);
        return hability;
    }

    @Before
    public void initTest() {
        hability = createEntity(em);
    }

    @Test
    @Transactional
    public void createHability() throws Exception {
        int databaseSizeBeforeCreate = habilityRepository.findAll().size();

        // Create the Hability
        restHabilityMockMvc.perform(post("/api/habilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hability)))
            .andExpect(status().isCreated());

        // Validate the Hability in the database
        List<Hability> habilityList = habilityRepository.findAll();
        assertThat(habilityList).hasSize(databaseSizeBeforeCreate + 1);
        Hability testHability = habilityList.get(habilityList.size() - 1);
        assertThat(testHability.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testHability.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHability.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testHability.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createHabilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = habilityRepository.findAll().size();

        // Create the Hability with an existing ID
        hability.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHabilityMockMvc.perform(post("/api/habilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hability)))
            .andExpect(status().isBadRequest());

        // Validate the Hability in the database
        List<Hability> habilityList = habilityRepository.findAll();
        assertThat(habilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = habilityRepository.findAll().size();
        // set the field null
        hability.setType(null);

        // Create the Hability, which fails.

        restHabilityMockMvc.perform(post("/api/habilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hability)))
            .andExpect(status().isBadRequest());

        List<Hability> habilityList = habilityRepository.findAll();
        assertThat(habilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = habilityRepository.findAll().size();
        // set the field null
        hability.setName(null);

        // Create the Hability, which fails.

        restHabilityMockMvc.perform(post("/api/habilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hability)))
            .andExpect(status().isBadRequest());

        List<Hability> habilityList = habilityRepository.findAll();
        assertThat(habilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHabilities() throws Exception {
        // Initialize the database
        habilityRepository.saveAndFlush(hability);

        // Get all the habilityList
        restHabilityMockMvc.perform(get("/api/habilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hability.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))));
    }
    
    @Test
    @Transactional
    public void getHability() throws Exception {
        // Initialize the database
        habilityRepository.saveAndFlush(hability);

        // Get the hability
        restHabilityMockMvc.perform(get("/api/habilities/{id}", hability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hability.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)));
    }

    @Test
    @Transactional
    public void getNonExistingHability() throws Exception {
        // Get the hability
        restHabilityMockMvc.perform(get("/api/habilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHability() throws Exception {
        // Initialize the database
        habilityService.save(hability);

        int databaseSizeBeforeUpdate = habilityRepository.findAll().size();

        // Update the hability
        Hability updatedHability = habilityRepository.findById(hability.getId()).get();
        // Disconnect from session so that the updates on updatedHability are not directly saved in db
        em.detach(updatedHability);
        updatedHability
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE);

        restHabilityMockMvc.perform(put("/api/habilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHability)))
            .andExpect(status().isOk());

        // Validate the Hability in the database
        List<Hability> habilityList = habilityRepository.findAll();
        assertThat(habilityList).hasSize(databaseSizeBeforeUpdate);
        Hability testHability = habilityList.get(habilityList.size() - 1);
        assertThat(testHability.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testHability.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHability.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testHability.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingHability() throws Exception {
        int databaseSizeBeforeUpdate = habilityRepository.findAll().size();

        // Create the Hability

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHabilityMockMvc.perform(put("/api/habilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hability)))
            .andExpect(status().isBadRequest());

        // Validate the Hability in the database
        List<Hability> habilityList = habilityRepository.findAll();
        assertThat(habilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHability() throws Exception {
        // Initialize the database
        habilityService.save(hability);

        int databaseSizeBeforeDelete = habilityRepository.findAll().size();

        // Delete the hability
        restHabilityMockMvc.perform(delete("/api/habilities/{id}", hability.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hability> habilityList = habilityRepository.findAll();
        assertThat(habilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hability.class);
        Hability hability1 = new Hability();
        hability1.setId(1L);
        Hability hability2 = new Hability();
        hability2.setId(hability1.getId());
        assertThat(hability1).isEqualTo(hability2);
        hability2.setId(2L);
        assertThat(hability1).isNotEqualTo(hability2);
        hability1.setId(null);
        assertThat(hability1).isNotEqualTo(hability2);
    }
}
