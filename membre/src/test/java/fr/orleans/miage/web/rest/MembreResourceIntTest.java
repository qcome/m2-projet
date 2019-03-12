package fr.orleans.miage.web.rest;

import fr.orleans.miage.MembreApp;

import fr.orleans.miage.domain.Membre;
import fr.orleans.miage.repository.MembreRepository;
import fr.orleans.miage.web.rest.errors.ExceptionTranslator;

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


import static fr.orleans.miage.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MembreResource REST controller.
 *
 * @see MembreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MembreApp.class)
public class MembreResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    @Autowired
    private MembreRepository membreRepository;

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

    private MockMvc restMembreMockMvc;

    private Membre membre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MembreResource membreResource = new MembreResource(membreRepository);
        this.restMembreMockMvc = MockMvcBuilders.standaloneSetup(membreResource)
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
    public static Membre createEntity(EntityManager em) {
        Membre membre = new Membre()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .ville(DEFAULT_VILLE);
        return membre;
    }

    @Before
    public void initTest() {
        membre = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembre() throws Exception {
        int databaseSizeBeforeCreate = membreRepository.findAll().size();

        // Create the Membre
        restMembreMockMvc.perform(post("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membre)))
            .andExpect(status().isCreated());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeCreate + 1);
        Membre testMembre = membreList.get(membreList.size() - 1);
        assertThat(testMembre.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMembre.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMembre.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMembre.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testMembre.getVille()).isEqualTo(DEFAULT_VILLE);
    }

    @Test
    @Transactional
    public void createMembreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membreRepository.findAll().size();

        // Create the Membre with an existing ID
        membre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreMockMvc.perform(post("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membre)))
            .andExpect(status().isBadRequest());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMembres() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        // Get all the membreList
        restMembreMockMvc.perform(get("/api/membres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())));
    }
    
    @Test
    @Transactional
    public void getMembre() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        // Get the membre
        restMembreMockMvc.perform(get("/api/membres/{id}", membre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(membre.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMembre() throws Exception {
        // Get the membre
        restMembreMockMvc.perform(get("/api/membres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembre() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        int databaseSizeBeforeUpdate = membreRepository.findAll().size();

        // Update the membre
        Membre updatedMembre = membreRepository.findById(membre.getId()).get();
        // Disconnect from session so that the updates on updatedMembre are not directly saved in db
        em.detach(updatedMembre);
        updatedMembre
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .ville(UPDATED_VILLE);

        restMembreMockMvc.perform(put("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembre)))
            .andExpect(status().isOk());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
        Membre testMembre = membreList.get(membreList.size() - 1);
        assertThat(testMembre.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembre.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMembre.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMembre.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testMembre.getVille()).isEqualTo(UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMembre() throws Exception {
        int databaseSizeBeforeUpdate = membreRepository.findAll().size();

        // Create the Membre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreMockMvc.perform(put("/api/membres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membre)))
            .andExpect(status().isBadRequest());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembre() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        int databaseSizeBeforeDelete = membreRepository.findAll().size();

        // Delete the membre
        restMembreMockMvc.perform(delete("/api/membres/{id}", membre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Membre.class);
        Membre membre1 = new Membre();
        membre1.setId(1L);
        Membre membre2 = new Membre();
        membre2.setId(membre1.getId());
        assertThat(membre1).isEqualTo(membre2);
        membre2.setId(2L);
        assertThat(membre1).isNotEqualTo(membre2);
        membre1.setId(null);
        assertThat(membre1).isNotEqualTo(membre2);
    }
}
