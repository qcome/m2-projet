package fr.orleans.miage.web.rest;

import fr.orleans.miage.domain.EvenementGlobal;
import fr.orleans.miage.service.EvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/evenement")
public class EvenementResource {

    @Autowired
    private EvenementService evenementService;

    @GetMapping("/")
    public Page<EvenementGlobal> getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "limit", defaultValue = "30") int limit) {

        return evenementService.getEvenementsPage(page, limit);
    }

    @GetMapping("/{id}")
    public EvenementGlobal getEvenement(@PathVariable Long id) {

        return evenementService.getEvenementById(id);
    }
}
