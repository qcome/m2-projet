package fr.orleans.miage.web.rest;

import fr.orleans.miage.EvenementSoireeApp;

import fr.orleans.miage.domain.EvenementSoiree;
import fr.orleans.miage.repository.EvenementSoireeRepository;
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
 * Test class for the EvenementSoireeResource REST controller.
 *
 * @see EvenementSoireeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EvenementSoireeApp.class)
public class EvenementSoireeResourceIntTest {

    private static final Long DEFAULT_ID_EVENEMENT = 1L;
    private static final Long UPDATED_ID_EVENEMENT = 2L;

    private static final Long DEFAULT_ID_SOIREE = 1L;
    private static final Long UPDATED_ID_SOIREE = 2L;

    @Autowired
    private EvenementSoireeRepository evenementSoireeRepository;

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

    private MockMvc restEvenementSoireeMockMvc;

    private EvenementSoiree evenementSoiree;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvenementSoireeResource evenementSoireeResource = new EvenementSoireeResource(evenementSoireeRepository);
        this.restEvenementSoireeMockMvc = MockMvcBuilders.standaloneSetup(evenementSoireeResource)
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
    public static EvenementSoiree createEntity(EntityManager em) {
        EvenementSoiree evenementSoiree = new EvenementSoiree()
            .idEvenement(DEFAULT_ID_EVENEMENT)
            .idSoiree(DEFAULT_ID_SOIREE);
        return evenementSoiree;
    }

    @Before
    public void initTest() {
        evenementSoiree = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvenementSoiree() throws Exception {
        int databaseSizeBeforeCreate = evenementSoireeRepository.findAll().size();

        // Create the EvenementSoiree
        restEvenementSoireeMockMvc.perform(post("/api/evenement-soirees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementSoiree)))
            .andExpect(status().isCreated());

        // Validate the EvenementSoiree in the database
        List<EvenementSoiree> evenementSoireeList = evenementSoireeRepository.findAll();
        assertThat(evenementSoireeList).hasSize(databaseSizeBeforeCreate + 1);
        EvenementSoiree testEvenementSoiree = evenementSoireeList.get(evenementSoireeList.size() - 1);
        assertThat(testEvenementSoiree.getIdEvenement()).isEqualTo(DEFAULT_ID_EVENEMENT);
        assertThat(testEvenementSoiree.getIdSoiree()).isEqualTo(DEFAULT_ID_SOIREE);
    }

    @Test
    @Transactional
    public void createEvenementSoireeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evenementSoireeRepository.findAll().size();

        // Create the EvenementSoiree with an existing ID
        evenementSoiree.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvenementSoireeMockMvc.perform(post("/api/evenement-soirees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementSoiree)))
            .andExpect(status().isBadRequest());

        // Validate the EvenementSoiree in the database
        List<EvenementSoiree> evenementSoireeList = evenementSoireeRepository.findAll();
        assertThat(evenementSoireeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvenementSoirees() throws Exception {
        // Initialize the database
        evenementSoireeRepository.saveAndFlush(evenementSoiree);

        // Get all the evenementSoireeList
        restEvenementSoireeMockMvc.perform(get("/api/evenement-soirees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evenementSoiree.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEvenement").value(hasItem(DEFAULT_ID_EVENEMENT.intValue())))
            .andExpect(jsonPath("$.[*].idSoiree").value(hasItem(DEFAULT_ID_SOIREE.intValue())));
    }
    
    @Test
    @Transactional
    public void getEvenementSoiree() throws Exception {
        // Initialize the database
        evenementSoireeRepository.saveAndFlush(evenementSoiree);

        // Get the evenementSoiree
        restEvenementSoireeMockMvc.perform(get("/api/evenement-soirees/{id}", evenementSoiree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evenementSoiree.getId().intValue()))
            .andExpect(jsonPath("$.idEvenement").value(DEFAULT_ID_EVENEMENT.intValue()))
            .andExpect(jsonPath("$.idSoiree").value(DEFAULT_ID_SOIREE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEvenementSoiree() throws Exception {
        // Get the evenementSoiree
        restEvenementSoireeMockMvc.perform(get("/api/evenement-soirees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvenementSoiree() throws Exception {
        // Initialize the database
        evenementSoireeRepository.saveAndFlush(evenementSoiree);

        int databaseSizeBeforeUpdate = evenementSoireeRepository.findAll().size();

        // Update the evenementSoiree
        EvenementSoiree updatedEvenementSoiree = evenementSoireeRepository.findById(evenementSoiree.getId()).get();
        // Disconnect from session so that the updates on updatedEvenementSoiree are not directly saved in db
        em.detach(updatedEvenementSoiree);
        updatedEvenementSoiree
            .idEvenement(UPDATED_ID_EVENEMENT)
            .idSoiree(UPDATED_ID_SOIREE);

        restEvenementSoireeMockMvc.perform(put("/api/evenement-soirees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvenementSoiree)))
            .andExpect(status().isOk());

        // Validate the EvenementSoiree in the database
        List<EvenementSoiree> evenementSoireeList = evenementSoireeRepository.findAll();
        assertThat(evenementSoireeList).hasSize(databaseSizeBeforeUpdate);
        EvenementSoiree testEvenementSoiree = evenementSoireeList.get(evenementSoireeList.size() - 1);
        assertThat(testEvenementSoiree.getIdEvenement()).isEqualTo(UPDATED_ID_EVENEMENT);
        assertThat(testEvenementSoiree.getIdSoiree()).isEqualTo(UPDATED_ID_SOIREE);
    }

    @Test
    @Transactional
    public void updateNonExistingEvenementSoiree() throws Exception {
        int databaseSizeBeforeUpdate = evenementSoireeRepository.findAll().size();

        // Create the EvenementSoiree

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvenementSoireeMockMvc.perform(put("/api/evenement-soirees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenementSoiree)))
            .andExpect(status().isBadRequest());

        // Validate the EvenementSoiree in the database
        List<EvenementSoiree> evenementSoireeList = evenementSoireeRepository.findAll();
        assertThat(evenementSoireeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvenementSoiree() throws Exception {
        // Initialize the database
        evenementSoireeRepository.saveAndFlush(evenementSoiree);

        int databaseSizeBeforeDelete = evenementSoireeRepository.findAll().size();

        // Delete the evenementSoiree
        restEvenementSoireeMockMvc.perform(delete("/api/evenement-soirees/{id}", evenementSoiree.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EvenementSoiree> evenementSoireeList = evenementSoireeRepository.findAll();
        assertThat(evenementSoireeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvenementSoiree.class);
        EvenementSoiree evenementSoiree1 = new EvenementSoiree();
        evenementSoiree1.setId(1L);
        EvenementSoiree evenementSoiree2 = new EvenementSoiree();
        evenementSoiree2.setId(evenementSoiree1.getId());
        assertThat(evenementSoiree1).isEqualTo(evenementSoiree2);
        evenementSoiree2.setId(2L);
        assertThat(evenementSoiree1).isNotEqualTo(evenementSoiree2);
        evenementSoiree1.setId(null);
        assertThat(evenementSoiree1).isNotEqualTo(evenementSoiree2);
    }
}
