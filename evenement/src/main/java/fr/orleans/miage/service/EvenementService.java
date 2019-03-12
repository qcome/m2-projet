package fr.orleans.miage.service;

import fr.orleans.miage.domain.EvenementGlobal;
import fr.orleans.miage.repository.EvenementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class EvenementService {

    private final Logger log = LoggerFactory.getLogger(EvenementService.class);

    @Autowired
    private final EvenementRepository evenementRepository;

    public EvenementService(EvenementRepository evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    public void insertEvenements(List<EvenementGlobal> evenementGlobals){

        evenementRepository.saveAll(evenementGlobals);
    }

    public void insertEvenement(EvenementGlobal evenementGlobal){
        evenementRepository.save(evenementGlobal);
    }

    public EvenementGlobal getFirstEvenement(){
        List<EvenementGlobal> evenementGlobal = evenementRepository.findOne(PageRequest.of(0,1));
        return evenementGlobal.isEmpty() ? null : evenementGlobal.get(0);
    }

    public EvenementGlobal getEvenementById(Long id){
        Optional<EvenementGlobal> evenement = evenementRepository.findById(id);
        return evenement.orElse(null);
    }

    public void updateEvenements(List<EvenementGlobal> evenementGlobals){
        for(EvenementGlobal event : evenementGlobals){

            EvenementGlobal evenementGlobalAInserer = Optional.of(evenementRepository
                .findByUid(event.getUid()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(evenement -> {
                    evenement.setTitre(event.getTitre());
                    evenement.setAdresse(event.getAdresse());
                    evenement.setCoordonneesList(event.getCoordonneesList());
                    evenement.setDateDebut(event.getDateDebut());
                    evenement.setDateFin(event.getDateFin());
                    evenement.setDateUpdate(event.getDateUpdate());
                    evenement.setDepartement(event.getDepartement());
                    evenement.setDescription(event.getDescription());
                    evenement.setEmplacement(event.getEmplacement());
                    try {
                        evenement.setHorairesList(event.getHorairesList());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    evenement.setImage(event.getImage());
                    evenement.setImageVignette(event.getImageVignette());
                    evenement.setInfoLieuDate(event.getInfoLieuDate());
                    evenement.setInfos(event.getInfos());
                    evenement.setMotsCles(event.getMotsCles());
                    evenement.setRegion(event.getRegion());
                    evenement.setTarif(event.getTarif());
                    evenement.setVille(event.getVille());
                    return evenement;
                }).orElse(event);

            insertEvenement(evenementGlobalAInserer);



        }
    }

    public List<EvenementGlobal> getEvenements(){
        return evenementRepository.findAll();
    }

    public Page<EvenementGlobal> getEvenementsPage(int page, int limit) {
        return evenementRepository.findAll(PageRequest.of(page, limit));
    }
}
