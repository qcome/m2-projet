package fr.orleans.miage.repository;

import fr.orleans.miage.domain.Soiree;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Soiree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoireeRepository extends JpaRepository<Soiree, Long> {

}
