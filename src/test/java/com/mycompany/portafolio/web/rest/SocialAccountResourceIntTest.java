package com.mycompany.portafolio.web.rest;

import com.mycompany.portafolio.PortafolioApp;

import com.mycompany.portafolio.domain.SocialAccount;
import com.mycompany.portafolio.domain.Contact;
import com.mycompany.portafolio.repository.SocialAccountRepository;
import com.mycompany.portafolio.service.SocialAccountService;
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

import com.mycompany.portafolio.domain.enumeration.Network;
/**
 * Test class for the SocialAccountResource REST controller.
 *
 * @see SocialAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortafolioApp.class)
public class SocialAccountResourceIntTest {

    private static final Network DEFAULT_NETWORK = Network.GITHUB;
    private static final Network UPDATED_NETWORK = Network.TWITTER;

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_NETWORK = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_NETWORK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    @Autowired
    private SocialAccountService socialAccountService;

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

    private MockMvc restSocialAccountMockMvc;

    private SocialAccount socialAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocialAccountResource socialAccountResource = new SocialAccountResource(socialAccountService);
        this.restSocialAccountMockMvc = MockMvcBuilders.standaloneSetup(socialAccountResource)
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
    public static SocialAccount createEntity(EntityManager em) {
        SocialAccount socialAccount = new SocialAccount()
            .network(DEFAULT_NETWORK)
            .user(DEFAULT_USER)
            .otherNetwork(DEFAULT_OTHER_NETWORK)
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE);
        // Add required entity
        Contact contact = ContactResourceIntTest.createEntity(em);
        em.persist(contact);
        em.flush();
        socialAccount.setContact(contact);
        return socialAccount;
    }

    @Before
    public void initTest() {
        socialAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocialAccount() throws Exception {
        int databaseSizeBeforeCreate = socialAccountRepository.findAll().size();

        // Create the SocialAccount
        restSocialAccountMockMvc.perform(post("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialAccount)))
            .andExpect(status().isCreated());

        // Validate the SocialAccount in the database
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeCreate + 1);
        SocialAccount testSocialAccount = socialAccountList.get(socialAccountList.size() - 1);
        assertThat(testSocialAccount.getNetwork()).isEqualTo(DEFAULT_NETWORK);
        assertThat(testSocialAccount.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testSocialAccount.getOtherNetwork()).isEqualTo(DEFAULT_OTHER_NETWORK);
        assertThat(testSocialAccount.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testSocialAccount.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSocialAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialAccountRepository.findAll().size();

        // Create the SocialAccount with an existing ID
        socialAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialAccountMockMvc.perform(post("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialAccount)))
            .andExpect(status().isBadRequest());

        // Validate the SocialAccount in the database
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNetworkIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialAccountRepository.findAll().size();
        // set the field null
        socialAccount.setNetwork(null);

        // Create the SocialAccount, which fails.

        restSocialAccountMockMvc.perform(post("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialAccount)))
            .andExpect(status().isBadRequest());

        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialAccountRepository.findAll().size();
        // set the field null
        socialAccount.setUser(null);

        // Create the SocialAccount, which fails.

        restSocialAccountMockMvc.perform(post("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialAccount)))
            .andExpect(status().isBadRequest());

        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSocialAccounts() throws Exception {
        // Initialize the database
        socialAccountRepository.saveAndFlush(socialAccount);

        // Get all the socialAccountList
        restSocialAccountMockMvc.perform(get("/api/social-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].network").value(hasItem(DEFAULT_NETWORK.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].otherNetwork").value(hasItem(DEFAULT_OTHER_NETWORK.toString())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))));
    }
    
    @Test
    @Transactional
    public void getSocialAccount() throws Exception {
        // Initialize the database
        socialAccountRepository.saveAndFlush(socialAccount);

        // Get the socialAccount
        restSocialAccountMockMvc.perform(get("/api/social-accounts/{id}", socialAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(socialAccount.getId().intValue()))
            .andExpect(jsonPath("$.network").value(DEFAULT_NETWORK.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.otherNetwork").value(DEFAULT_OTHER_NETWORK.toString()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)));
    }

    @Test
    @Transactional
    public void getNonExistingSocialAccount() throws Exception {
        // Get the socialAccount
        restSocialAccountMockMvc.perform(get("/api/social-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialAccount() throws Exception {
        // Initialize the database
        socialAccountService.save(socialAccount);

        int databaseSizeBeforeUpdate = socialAccountRepository.findAll().size();

        // Update the socialAccount
        SocialAccount updatedSocialAccount = socialAccountRepository.findById(socialAccount.getId()).get();
        // Disconnect from session so that the updates on updatedSocialAccount are not directly saved in db
        em.detach(updatedSocialAccount);
        updatedSocialAccount
            .network(UPDATED_NETWORK)
            .user(UPDATED_USER)
            .otherNetwork(UPDATED_OTHER_NETWORK)
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE);

        restSocialAccountMockMvc.perform(put("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocialAccount)))
            .andExpect(status().isOk());

        // Validate the SocialAccount in the database
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeUpdate);
        SocialAccount testSocialAccount = socialAccountList.get(socialAccountList.size() - 1);
        assertThat(testSocialAccount.getNetwork()).isEqualTo(UPDATED_NETWORK);
        assertThat(testSocialAccount.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testSocialAccount.getOtherNetwork()).isEqualTo(UPDATED_OTHER_NETWORK);
        assertThat(testSocialAccount.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testSocialAccount.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSocialAccount() throws Exception {
        int databaseSizeBeforeUpdate = socialAccountRepository.findAll().size();

        // Create the SocialAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialAccountMockMvc.perform(put("/api/social-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialAccount)))
            .andExpect(status().isBadRequest());

        // Validate the SocialAccount in the database
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSocialAccount() throws Exception {
        // Initialize the database
        socialAccountService.save(socialAccount);

        int databaseSizeBeforeDelete = socialAccountRepository.findAll().size();

        // Delete the socialAccount
        restSocialAccountMockMvc.perform(delete("/api/social-accounts/{id}", socialAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SocialAccount> socialAccountList = socialAccountRepository.findAll();
        assertThat(socialAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialAccount.class);
        SocialAccount socialAccount1 = new SocialAccount();
        socialAccount1.setId(1L);
        SocialAccount socialAccount2 = new SocialAccount();
        socialAccount2.setId(socialAccount1.getId());
        assertThat(socialAccount1).isEqualTo(socialAccount2);
        socialAccount2.setId(2L);
        assertThat(socialAccount1).isNotEqualTo(socialAccount2);
        socialAccount1.setId(null);
        assertThat(socialAccount1).isNotEqualTo(socialAccount2);
    }
}
