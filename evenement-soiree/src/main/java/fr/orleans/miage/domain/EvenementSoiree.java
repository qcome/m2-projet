package fr.orleans.miage.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EvenementSoiree.
 */
@Entity
@Table(name = "evenement_soiree")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EvenementSoiree implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_evenement")
    private Long idEvenement;

    @Column(name = "id_soiree")
    private Long idSoiree;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEvenement() {
        return idEvenement;
    }

    public EvenementSoiree idEvenement(Long idEvenement) {
        this.idEvenement = idEvenement;
        return this;
    }

    public void setIdEvenement(Long idEvenement) {
        this.idEvenement = idEvenement;
    }

    public Long getIdSoiree() {
        return idSoiree;
    }

    public EvenementSoiree idSoiree(Long idSoiree) {
        this.idSoiree = idSoiree;
        return this;
    }

    public void setIdSoiree(Long idSoiree) {
        this.idSoiree = idSoiree;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EvenementSoiree evenementSoiree = (EvenementSoiree) o;
        if (evenementSoiree.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evenementSoiree.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvenementSoiree{" +
            "id=" + getId() +
            ", idEvenement=" + getIdEvenement() +
            ", idSoiree=" + getIdSoiree() +
            "}";
    }
}
