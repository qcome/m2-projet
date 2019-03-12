package fr.orleans.miage.web.rest;
import fr.orleans.miage.domain.Soiree;
import fr.orleans.miage.repository.SoireeRepository;
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
 * REST controller for managing Soiree.
 */
@RestController
@RequestMapping("/api")
public class SoireeResource {

    private final Logger log = LoggerFactory.getLogger(SoireeResource.class);

    private static final String ENTITY_NAME = "soireeSoiree";

    private final SoireeRepository soireeRepository;

    public SoireeResource(SoireeRepository soireeRepository) {
        this.soireeRepository = soireeRepository;
    }

    /**
     * POST  /soirees : Create a new soiree.
     *
     * @param soiree the soiree to create
     * @return the ResponseEntity with status 201 (Created) and with body the new soiree, or with status 400 (Bad Request) if the soiree has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/soirees")
    public ResponseEntity<Soiree> createSoiree(@RequestBody Soiree soiree) throws URISyntaxException {
        log.debug("REST request to save Soiree : {}", soiree);
        if (soiree.getId() != null) {
            throw new BadRequestAlertException("A new soiree cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Soiree result = soireeRepository.save(soiree);
        return ResponseEntity.created(new URI("/api/soirees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /soirees : Updates an existing soiree.
     *
     * @param soiree the soiree to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated soiree,
     * or with status 400 (Bad Request) if the soiree is not valid,
     * or with status 500 (Internal Server Error) if the soiree couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/soirees")
    public ResponseEntity<Soiree> updateSoiree(@RequestBody Soiree soiree) throws URISyntaxException {
        log.debug("REST request to update Soiree : {}", soiree);
        if (soiree.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Soiree result = soireeRepository.save(soiree);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, soiree.getId().toString()))
            .body(result);
    }

    /**
     * GET  /soirees : get all the soirees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of soirees in body
     */
    @GetMapping("/soirees")
    public List<Soiree> getAllSoirees() {
        log.debug("REST request to get all Soirees");
        return soireeRepository.findAll();
    }

    /**
     * GET  /soirees/:id : get the "id" soiree.
     *
     * @param id the id of the soiree to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the soiree, or with status 404 (Not Found)
     */
    @GetMapping("/soirees/{id}")
    public ResponseEntity<Soiree> getSoiree(@PathVariable Long id) {
        log.debug("REST request to get Soiree : {}", id);
        Optional<Soiree> soiree = soireeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(soiree);
    }

    /**
     * DELETE  /soirees/:id : delete the "id" soiree.
     *
     * @param id the id of the soiree to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/soirees/{id}")
    public ResponseEntity<Void> deleteSoiree(@PathVariable Long id) {
        log.debug("REST request to delete Soiree : {}", id);
        soireeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
