package fr.orleans.miage.web.rest;
import fr.orleans.miage.domain.Membre;
import fr.orleans.miage.repository.MembreRepository;
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
 * REST controller for managing Membre.
 */
@RestController
@RequestMapping("/api")
public class MembreResource {

    private final Logger log = LoggerFactory.getLogger(MembreResource.class);

    private static final String ENTITY_NAME = "membreMembre";

    private final MembreRepository membreRepository;

    public MembreResource(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    /**
     * POST  /membres : Create a new membre.
     *
     * @param membre the membre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membre, or with status 400 (Bad Request) if the membre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/membres")
    public ResponseEntity<Membre> createMembre(@RequestBody Membre membre) throws URISyntaxException {
        log.debug("REST request to save Membre : {}", membre);
        if (membre.getId() != null) {
            throw new BadRequestAlertException("A new membre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Membre result = membreRepository.save(membre);
        return ResponseEntity.created(new URI("/api/membres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /membres : Updates an existing membre.
     *
     * @param membre the membre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membre,
     * or with status 400 (Bad Request) if the membre is not valid,
     * or with status 500 (Internal Server Error) if the membre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/membres")
    public ResponseEntity<Membre> updateMembre(@RequestBody Membre membre) throws URISyntaxException {
        log.debug("REST request to update Membre : {}", membre);
        if (membre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Membre result = membreRepository.save(membre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, membre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /membres : get all the membres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of membres in body
     */
    @GetMapping("/membres")
    public List<Membre> getAllMembres() {
        log.debug("REST request to get all Membres");
        return membreRepository.findAll();
    }

    /**
     * GET  /membres/:id : get the "id" membre.
     *
     * @param id the id of the membre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membre, or with status 404 (Not Found)
     */
    @GetMapping("/membres/{id}")
    public ResponseEntity<Membre> getMembre(@PathVariable Long id) {
        log.debug("REST request to get Membre : {}", id);
        Optional<Membre> membre = membreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(membre);
    }

    /**
     * DELETE  /membres/:id : delete the "id" membre.
     *
     * @param id the id of the membre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/membres/{id}")
    public ResponseEntity<Void> deleteMembre(@PathVariable Long id) {
        log.debug("REST request to delete Membre : {}", id);
        membreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
