package fr.orleans.miage.service;

import fr.orleans.miage.domain.EvenementAgglo;
import fr.orleans.miage.domain.EvenementPerso;
import fr.orleans.miage.repository.EvenementAggloRepository;
import fr.orleans.miage.repository.EvenementPersoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvenementPersoService {
    @Autowired
    EvenementPersoRepository evenementPersoRepository;

    public void delete(Long id){
        this.evenementPersoRepository.deleteById(id);
    }


    public EvenementPerso saveEvenementPerso(EvenementPerso evenementPerso){
        return this.evenementPersoRepository.save(evenementPerso);
    }
    public void insertEvenementsPerso(List<EvenementPerso> evenementsPerso){
        this.evenementPersoRepository.saveAll(evenementsPerso);
    }

    public List<EvenementPerso> getEvenementPersoByUser(Long idUser){
        return this.evenementPersoRepository.findByIdUser(idUser);
    }

    public Optional<EvenementPerso> getEvenementPersoById(Long id){
         return evenementPersoRepository.findById(id);
    }

    public List<EvenementPerso> getEvenementsPerso(){
        return evenementPersoRepository.findAll();
    }
}
