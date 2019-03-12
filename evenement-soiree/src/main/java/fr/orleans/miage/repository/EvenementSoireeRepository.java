package fr.orleans.miage.repository;

import fr.orleans.miage.domain.EvenementSoiree;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EvenementSoiree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvenementSoireeRepository extends JpaRepository<EvenementSoiree, Long> {

}
