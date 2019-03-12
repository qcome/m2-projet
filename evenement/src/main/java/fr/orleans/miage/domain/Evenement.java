package fr.orleans.miage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date horairesDebut;
    private Date horairesFin;

    @JsonIgnoreProperties("evenements")
    @ManyToOne(fetch = FetchType.LAZY)
    private EvenementGlobal evenementGlobal;

    public Evenement(Date horairesDebut, Date horairesFin, EvenementGlobal evenementGlobal) {
        this.horairesDebut = horairesDebut;
        this.horairesFin = horairesFin;
        this.evenementGlobal = evenementGlobal;
    }
}
