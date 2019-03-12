package fr.orleans.miage.init;

import fr.orleans.miage.jsonwithjackson.EvenementJsonNode;
import fr.orleans.miage.service.EvenementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@Component
public class DataEvenementsInit implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataEvenementsInit.class);

    @Autowired
    EvenementService evenementService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //si aucun evenement dans la table "evenement"
        if(evenementService.getFirstEvenement() == null){
            log.info("========IMPORT DES EVENEMENTS ORLEANS===========");
            EvenementJsonNode jsonNode = null;
            try {
                jsonNode = new EvenementJsonNode();
                evenementService.insertEvenements(jsonNode.getAllEventsWithJsonNode());
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("========FIN IMPORT DES EVENEMENTS ORLEANS===========");
        }else{
            updateEvenements();
        }


    }

    public void updateEvenements(){
        log.info("========MAJ DES EVENEMENTS ORLEANS===========");
        EvenementJsonNode jsonNode = null;
        try {
            jsonNode = new EvenementJsonNode();
            evenementService.updateEvenements(jsonNode.getLastEventsUpdatedWithJsonNode(new Date(System.currentTimeMillis()-24*60*60*1000*2)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("========FIN MAJ DES EVENEMENTS ORLEANS===========");
    }

}
