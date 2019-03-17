package fr.orleans.miage.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Horaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date horairesDebut;
    private Date horairesFin;


    @JsonBackReference
    @ApiModelProperty(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Evenement evenement;

    public Horaire() {
    }

    public Horaire(Date horairesDebut, Date horairesFin, Evenement evenement) {
        this.horairesDebut = horairesDebut;
        this.horairesFin = horairesFin;
        this.evenement = evenement;
    }

    public Date getHorairesDebut() {
        return horairesDebut;
    }

    public void setHorairesDebut(Date horairesDebut) {
        this.horairesDebut = horairesDebut;
    }

    public Date getHorairesFin() {
        return horairesFin;
    }

    public void setHorairesFin(Date horairesFin) {
        this.horairesFin = horairesFin;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Horaire{" +
            "id=" + id +
            ", horairesDebut=" + horairesDebut +
            ", horairesFin=" + horairesFin +
            '}';
    }
}
