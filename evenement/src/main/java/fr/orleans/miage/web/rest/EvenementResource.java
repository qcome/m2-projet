package fr.orleans.miage.web.rest;

import fr.orleans.miage.domain.Evenement;
import fr.orleans.miage.domain.EvenementAgglo;
import fr.orleans.miage.domain.EvenementPerso;
import fr.orleans.miage.service.EvenementAggloService;
import fr.orleans.miage.service.EvenementPersoService;
import fr.orleans.miage.service.EvenementService;
import fr.orleans.miage.web.rest.errors.EvenementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/evenement")
public class EvenementResource {



    @Autowired
    private EvenementService evenementService;

    @Autowired
    EvenementAggloService evenementAggloService;

    @Autowired
    EvenementPersoService evenementPersoService;



    @GetMapping("/")
    public Page<Evenement> getEvenements(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "limit", defaultValue = "30") int limit){
        return evenementService.getEvenementsPage(page, limit);
    }


    @GetMapping("/agglo")
    public Page<EvenementAgglo> getEvenementsAgglo(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "limit", defaultValue = "30") int limit){
        return evenementAggloService.getEvenementsAggloPage(page, limit);
    }

    @GetMapping("/agglo/{id}")
    public EvenementAgglo getEvenementAgglo(@PathVariable Long id){
        return evenementAggloService.getEvenementAggloById(id);
    }

    @GetMapping("/perso")
    public List<EvenementPerso> getEvenementsPerso(@RequestParam(value = "idUser", required = false) Long idUser){
        if(idUser == null){
            return evenementPersoService.getEvenementsPerso();
        }
        return evenementPersoService.getEvenementPersoByUser(idUser);
    }

    @GetMapping("/perso/{id}")
    public EvenementPerso getEvenementPerso(@PathVariable Long id){
        return evenementPersoService.getEvenementPersoById(id).orElseThrow(() -> new EvenementNotFoundException(id));
    }

    @PostMapping("/perso")
    public EvenementPerso newEvenementPerso(@RequestBody EvenementPerso evenementPerso){
        return evenementPersoService.saveEvenementPerso(evenementPerso);
    }

    @PutMapping("/perso/{id}")
    public EvenementPerso replaceEvenementPerso(@RequestBody EvenementPerso newEvenementPerso, @PathVariable Long id){
        return evenementPersoService.getEvenementPersoById(id)
            .map(evenementPerso -> {
                evenementPerso.setHoraires(newEvenementPerso.getHoraires());
                return evenementPersoService.saveEvenementPerso(evenementPerso);
            }).orElseGet(() -> {
                newEvenementPerso.setId(id);
                return evenementPersoService.saveEvenementPerso(newEvenementPerso);
            });
    }

    @DeleteMapping("/perso/{id}")
    public void deleteEvenementPerso(@PathVariable Long id){
        evenementPersoService.delete(id);
    }
}
