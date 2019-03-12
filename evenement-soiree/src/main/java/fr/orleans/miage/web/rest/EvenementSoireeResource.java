package fr.orleans.miage.web.rest;
import fr.orleans.miage.domain.EvenementSoiree;
import fr.orleans.miage.repository.EvenementSoireeRepository;
import fr.orleans.miage.web.rest.errors.BadRequestAlertException;
import fr.orleans.miage.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EvenementSoiree.
 */
@RestController
@RequestMapping("/api")
public class EvenementSoireeResource {

    private final Logger log = LoggerFactory.getLogger(EvenementSoireeResource.class);

    private static final String ENTITY_NAME = "evenementSoireeEvenementSoiree";

    private final EvenementSoireeRepository evenementSoireeRepository;

    public EvenementSoireeResource(EvenementSoireeRepository evenementSoireeRepository) {
        this.evenementSoireeRepository = evenementSoireeRepository;
    }

    /**
     * POST  /evenement-soirees : Create a new evenementSoiree.
     *
     * @param evenementSoiree the evenementSoiree to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evenementSoiree, or with status 400 (Bad Request) if the evenementSoiree has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evenement-soirees")
    public ResponseEntity<EvenementSoiree> createEvenementSoiree(@RequestBody EvenementSoiree evenementSoiree) throws URISyntaxException {
        log.debug("REST request to save EvenementSoiree : {}", evenementSoiree);
        if (evenementSoiree.getId() != null) {
            throw new BadRequestAlertException("A new evenementSoiree cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvenementSoiree result = evenementSoireeRepository.save(evenementSoiree);
        return ResponseEntity.created(new URI("/api/evenement-soirees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evenement-soirees : Updates an existing evenementSoiree.
     *
     * @param evenementSoiree the evenementSoiree to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evenementSoiree,
     * or with status 400 (Bad Request) if the evenementSoiree is not valid,
     * or with status 500 (Internal Server Error) if the evenementSoiree couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evenement-soirees")
    public ResponseEntity<EvenementSoiree> updateEvenementSoiree(@RequestBody EvenementSoiree evenementSoiree) throws URISyntaxException {
        log.debug("REST request to update EvenementSoiree : {}", evenementSoiree);
        if (evenementSoiree.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvenementSoiree result = evenementSoireeRepository.save(evenementSoiree);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evenementSoiree.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evenement-soirees : get all the evenementSoirees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evenementSoirees in body
     */
    @GetMapping("/evenement-soirees")
    public List<EvenementSoiree> getAllEvenementSoirees() {
        log.debug("REST request to get all EvenementSoirees");
        return evenementSoireeRepository.findAll();
    }

    /**
     * GET  /evenement-soirees/:id : get the "id" evenementSoiree.
     *
     * @param id the id of the evenementSoiree to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evenementSoiree, or with status 404 (Not Found)
     */
    @GetMapping("/evenement-soirees/{id}")
    public ResponseEntity<EvenementSoiree> getEvenementSoiree(@PathVariable Long id) {
        log.debug("REST request to get EvenementSoiree : {}", id);
        Optional<EvenementSoiree> evenementSoiree = evenementSoireeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(evenementSoiree);
    }

    /**
     * DELETE  /evenement-soirees/:id : delete the "id" evenementSoiree.
     *
     * @param id the id of the evenementSoiree to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evenement-soirees/{id}")
    public ResponseEntity<Void> deleteEvenementSoiree(@PathVariable Long id) {
        log.debug("REST request to delete EvenementSoiree : {}", id);
        evenementSoireeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
