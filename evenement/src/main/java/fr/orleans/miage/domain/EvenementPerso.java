package fr.orleans.miage.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
public class EvenementPerso extends Evenement{

    private Long idUser;

    public EvenementPerso() {
    }

    public EvenementPerso(Long idUser, String titre, String description, String adresse, String ville) {
        super(titre, description, adresse, ville);

        this.idUser = idUser;
    }

    public void addHoraires(List<Date[]> horaires){
        for(Date[] horaire : horaires){
            this.addHoraire(new Horaire(horaire[0], horaire[1], this));
        }
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

}
