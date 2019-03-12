package fr.orleans.miage.web.rest;

import fr.orleans.miage.SoireeApp;

import fr.orleans.miage.domain.Soiree;
import fr.orleans.miage.repository.SoireeRepository;
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
 * Test class for the SoireeResource REST controller.
 *
 * @see SoireeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoireeApp.class)
public class SoireeResourceIntTest {

    private static final Long DEFAULT_ID_MEMBRE_ORGANISATEUR = 1L;
    private static final Long UPDATED_ID_MEMBRE_ORGANISATEUR = 2L;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SoireeRepository soireeRepository;

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

    private MockMvc restSoireeMockMvc;

    private Soiree soiree;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SoireeResource soireeResource = new SoireeResource(soireeRepository);
        this.restSoireeMockMvc = MockMvcBuilders.standaloneSetup(soireeResource)
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
    public static Soiree createEntity(EntityManager em) {
        Soiree soiree = new Soiree()
            .idMembreOrganisateur(DEFAULT_ID_MEMBRE_ORGANISATEUR)
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION);
        return soiree;
    }

    @Before
    public void initTest() {
        soiree = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoiree() throws Exception {
        int databaseSizeBeforeCreate = soireeRepository.findAll().size();

        // Create the Soiree
        restSoireeMockMvc.perform(post("/api/soirees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soiree)))
            .andExpect(status().isCreated());

        // Validate the Soiree in the database
        List<Soiree> soireeList = soireeRepository.findAll();
        assertThat(soireeList).hasSize(databaseSizeBeforeCreate + 1);
        Soiree testSoiree = soireeList.get(soireeList.size() - 1);
        assertThat(testSoiree.getIdMembreOrganisateur()).isEqualTo(DEFAULT_ID_MEMBRE_ORGANISATEUR);
        assertThat(testSoiree.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSoiree.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSoireeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = soireeRepository.findAll().size();

        // Create the Soiree with an existing ID
        soiree.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoireeMockMvc.perform(post("/api/soirees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soiree)))
            .andExpect(status().isBadRequest());

        // Validate the Soiree in the database
        List<Soiree> soireeList = soireeRepository.findAll();
        assertThat(soireeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSoirees() throws Exception {
        // Initialize the database
        soireeRepository.saveAndFlush(soiree);

        // Get all the soireeList
        restSoireeMockMvc.perform(get("/api/soirees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soiree.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMembreOrganisateur").value(hasItem(DEFAULT_ID_MEMBRE_ORGANISATEUR.intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSoiree() throws Exception {
        // Initialize the database
        soireeRepository.saveAndFlush(soiree);

        // Get the soiree
        restSoireeMockMvc.perform(get("/api/soirees/{id}", soiree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(soiree.getId().intValue()))
            .andExpect(jsonPath("$.idMembreOrganisateur").value(DEFAULT_ID_MEMBRE_ORGANISATEUR.intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSoiree() throws Exception {
        // Get the soiree
        restSoireeMockMvc.perform(get("/api/soirees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoiree() throws Exception {
        // Initialize the database
        soireeRepository.saveAndFlush(soiree);

        int databaseSizeBeforeUpdate = soireeRepository.findAll().size();

        // Update the soiree
        Soiree updatedSoiree = soireeRepository.findById(soiree.getId()).get();
        // Disconnect from session so that the updates on updatedSoiree are not directly saved in db
        em.detach(updatedSoiree);
        updatedSoiree
            .idMembreOrganisateur(UPDATED_ID_MEMBRE_ORGANISATEUR)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION);

        restSoireeMockMvc.perform(put("/api/soirees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSoiree)))
            .andExpect(status().isOk());

        // Validate the Soiree in the database
        List<Soiree> soireeList = soireeRepository.findAll();
        assertThat(soireeList).hasSize(databaseSizeBeforeUpdate);
        Soiree testSoiree = soireeList.get(soireeList.size() - 1);
        assertThat(testSoiree.getIdMembreOrganisateur()).isEqualTo(UPDATED_ID_MEMBRE_ORGANISATEUR);
        assertThat(testSoiree.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSoiree.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSoiree() throws Exception {
        int databaseSizeBeforeUpdate = soireeRepository.findAll().size();

        // Create the Soiree

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoireeMockMvc.perform(put("/api/soirees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soiree)))
            .andExpect(status().isBadRequest());

        // Validate the Soiree in the database
        List<Soiree> soireeList = soireeRepository.findAll();
        assertThat(soireeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSoiree() throws Exception {
        // Initialize the database
        soireeRepository.saveAndFlush(soiree);

        int databaseSizeBeforeDelete = soireeRepository.findAll().size();

        // Delete the soiree
        restSoireeMockMvc.perform(delete("/api/soirees/{id}", soiree.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Soiree> soireeList = soireeRepository.findAll();
        assertThat(soireeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soiree.class);
        Soiree soiree1 = new Soiree();
        soiree1.setId(1L);
        Soiree soiree2 = new Soiree();
        soiree2.setId(soiree1.getId());
        assertThat(soiree1).isEqualTo(soiree2);
        soiree2.setId(2L);
        assertThat(soiree1).isNotEqualTo(soiree2);
        soiree1.setId(null);
        assertThat(soiree1).isNotEqualTo(soiree2);
    }
}
