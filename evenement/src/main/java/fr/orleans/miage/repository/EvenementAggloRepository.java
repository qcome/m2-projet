package fr.orleans.miage.repository;

import fr.orleans.miage.domain.EvenementAgglo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EvenementAggloRepository extends JpaRepository<EvenementAgglo, Long> {
    @Query("SELECT e FROM EvenementAgglo e JOIN FETCH e.coordonnees ORDER BY e.id ASC")
    List<EvenementAgglo> findOne(Pageable page);
}
