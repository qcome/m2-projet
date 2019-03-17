package fr.orleans.miage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="EVENEMENT_TYPE")
public abstract class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(mappedBy = "evenement",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Horaire> horaires;

    private String titre;
    private String description;
    private String adresse;
    private String ville;

    public Evenement() {
        this.horaires = new ArrayList<>();
    }

    public Evenement(String titre, String description, String adresse, String ville) {
        this();
        this.titre = titre;
        this.description = description;
        this.adresse = adresse;
        this.ville = ville;
    }

    public void addHoraire(Horaire horaire){
        this.horaires.add(horaire);
    }

    public void removeHoraire(Horaire horaire){
        this.horaires.remove(horaire);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Horaire> getHoraires() {
        return horaires;
    }

    public void setHoraires(List<Horaire> horaires) {
        this.horaires = horaires;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
