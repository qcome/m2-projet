package fr.orleans.miage.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class Coordonnees {
    private Float lat;
    private Float lng;

    public Coordonnees(Float lat, Float lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
