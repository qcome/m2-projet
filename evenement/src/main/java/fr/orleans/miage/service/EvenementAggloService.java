package fr.orleans.miage.service;

import fr.orleans.miage.domain.EvenementAgglo;
import fr.orleans.miage.repository.EvenementAggloRepository;
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
public class EvenementAggloService {
    private final Logger log = LoggerFactory.getLogger(EvenementService.class);

    @Autowired
    private final EvenementAggloRepository evenementAggloRepository;

    public EvenementAggloService(EvenementAggloRepository evenementAggloRepository) {
        this.evenementAggloRepository = evenementAggloRepository;
    }


    public void insertEvenements(List<EvenementAgglo> evenementAgglos){
        for (EvenementAgglo evenementAgglo: evenementAgglos
            ) {
            try {
                evenementAgglo.addHoraires();
                System.out.println("evenementAgglo");
                System.out.println(evenementAgglo.getHoraires());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        evenementAggloRepository.saveAll(evenementAgglos);
    }

    public void insertEvenement(EvenementAgglo evenementAgglo){
        try {
            evenementAgglo.addHoraires();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        evenementAggloRepository.save(evenementAgglo);
    }

    public EvenementAgglo getFirstEvenement(){
        List<EvenementAgglo> evenementsAgglo = evenementAggloRepository.findOne(PageRequest.of(0,1));
        return evenementsAgglo.isEmpty() ? null : evenementsAgglo.get(0);
    }

    public Optional<EvenementAgglo> getEvenementById(Long id){
        return evenementAggloRepository.findById(id);
    }

   /* public void updateEvenements(List<EvenementGlobal> evenementGlobals){
        for(EvenementGlobal event : evenementGlobals){
            EvenementGlobal evenementGlobalAInserer = Optional.of(evenementGlobalRepository
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
                        evenement.addEvenementAgglo();
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
    }*/

    public List<EvenementAgglo> getEvenements(){
        return evenementAggloRepository.findAll();
    }

    public Page<EvenementAgglo> getEvenementsPage(int page, int limit) {
        return evenementAggloRepository.findAll(PageRequest.of(page, limit));
    }




    public void insertEvenementAgglo(EvenementAgglo evenementsAgglo){
        this.evenementAggloRepository.save(evenementsAgglo);
    }
    public void insertEvenementsAgglo(List<EvenementAgglo> evenementsAgglo){
        this.evenementAggloRepository.saveAll(evenementsAgglo);
    }

    public Page<EvenementAgglo> getEvenementsAggloPage(int page, int limit) {
        return evenementAggloRepository.findAll(PageRequest.of(page, limit));
    }

    public EvenementAgglo getEvenementAggloById(Long id){
        Optional<EvenementAgglo> evenement = evenementAggloRepository.findById(id);
        return evenement.orElse(null);
    }
}
