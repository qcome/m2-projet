package fr.orleans.miage.repository;

import fr.orleans.miage.domain.EvenementGlobal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvenementRepository extends JpaRepository<EvenementGlobal, Long> {

    Optional<EvenementGlobal> findByUid(Long uid);

    @Query("SELECT e FROM EvenementGlobal e ORDER BY e.id ASC")
    List<EvenementGlobal> findOne(Pageable page);
}
