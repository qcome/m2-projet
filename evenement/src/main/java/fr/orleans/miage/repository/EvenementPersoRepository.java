package fr.orleans.miage.repository;

import fr.orleans.miage.domain.EvenementPerso;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvenementPersoRepository extends JpaRepository<EvenementPerso, Long> {
    List<EvenementPerso> findByIdUser(Long idUser);
}
