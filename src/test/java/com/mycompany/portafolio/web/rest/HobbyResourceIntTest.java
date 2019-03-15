package com.mycompany.portafolio.web.rest;

import com.mycompany.portafolio.PortafolioApp;

import com.mycompany.portafolio.domain.Hobby;
import com.mycompany.portafolio.repository.HobbyRepository;
import com.mycompany.portafolio.service.HobbyService;
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
 * Test class for the HobbyResource REST controller.
 *
 * @see HobbyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortafolioApp.class)
public class HobbyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private HobbyService hobbyService;

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

    private MockMvc restHobbyMockMvc;

    private Hobby hobby;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HobbyResource hobbyResource = new HobbyResource(hobbyService);
        this.restHobbyMockMvc = MockMvcBuilders.standaloneSetup(hobbyResource)
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
    public static Hobby createEntity(EntityManager em) {
        Hobby hobby = new Hobby()
            .name(DEFAULT_NAME)
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE);
        return hobby;
    }

    @Before
    public void initTest() {
        hobby = createEntity(em);
    }

    @Test
    @Transactional
    public void createHobby() throws Exception {
        int databaseSizeBeforeCreate = hobbyRepository.findAll().size();

        // Create the Hobby
        restHobbyMockMvc.perform(post("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobby)))
            .andExpect(status().isCreated());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeCreate + 1);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHobby.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testHobby.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createHobbyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hobbyRepository.findAll().size();

        // Create the Hobby with an existing ID
        hobby.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHobbyMockMvc.perform(post("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobby)))
            .andExpect(status().isBadRequest());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hobbyRepository.findAll().size();
        // set the field null
        hobby.setName(null);

        // Create the Hobby, which fails.

        restHobbyMockMvc.perform(post("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobby)))
            .andExpect(status().isBadRequest());

        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHobbies() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        // Get all the hobbyList
        restHobbyMockMvc.perform(get("/api/hobbies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hobby.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))));
    }
    
    @Test
    @Transactional
    public void getHobby() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        // Get the hobby
        restHobbyMockMvc.perform(get("/api/hobbies/{id}", hobby.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hobby.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)));
    }

    @Test
    @Transactional
    public void getNonExistingHobby() throws Exception {
        // Get the hobby
        restHobbyMockMvc.perform(get("/api/hobbies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHobby() throws Exception {
        // Initialize the database
        hobbyService.save(hobby);

        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();

        // Update the hobby
        Hobby updatedHobby = hobbyRepository.findById(hobby.getId()).get();
        // Disconnect from session so that the updates on updatedHobby are not directly saved in db
        em.detach(updatedHobby);
        updatedHobby
            .name(UPDATED_NAME)
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE);

        restHobbyMockMvc.perform(put("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHobby)))
            .andExpect(status().isOk());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHobby.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testHobby.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingHobby() throws Exception {
        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();

        // Create the Hobby

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHobbyMockMvc.perform(put("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobby)))
            .andExpect(status().isBadRequest());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHobby() throws Exception {
        // Initialize the database
        hobbyService.save(hobby);

        int databaseSizeBeforeDelete = hobbyRepository.findAll().size();

        // Delete the hobby
        restHobbyMockMvc.perform(delete("/api/hobbies/{id}", hobby.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hobby.class);
        Hobby hobby1 = new Hobby();
        hobby1.setId(1L);
        Hobby hobby2 = new Hobby();
        hobby2.setId(hobby1.getId());
        assertThat(hobby1).isEqualTo(hobby2);
        hobby2.setId(2L);
        assertThat(hobby1).isNotEqualTo(hobby2);
        hobby1.setId(null);
        assertThat(hobby1).isNotEqualTo(hobby2);
    }
}
