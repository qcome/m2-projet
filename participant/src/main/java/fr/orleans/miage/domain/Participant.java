package fr.orleans.miage.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import fr.orleans.miage.domain.enumeration.EtatParticipation;

/**
 * A Participant.
 */
@Entity
@Table(name = "participant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_membre")
    private Long idMembre;

    @Column(name = "id_soiree")
    private Long idSoiree;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_participation")
    private EtatParticipation etatParticipation;

    @Column(name = "avis")
    private String avis;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMembre() {
        return idMembre;
    }

    public Participant idMembre(Long idMembre) {
        this.idMembre = idMembre;
        return this;
    }

    public void setIdMembre(Long idMembre) {
        this.idMembre = idMembre;
    }

    public Long getIdSoiree() {
        return idSoiree;
    }

    public Participant idSoiree(Long idSoiree) {
        this.idSoiree = idSoiree;
        return this;
    }

    public void setIdSoiree(Long idSoiree) {
        this.idSoiree = idSoiree;
    }

    public EtatParticipation getEtatParticipation() {
        return etatParticipation;
    }

    public Participant etatParticipation(EtatParticipation etatParticipation) {
        this.etatParticipation = etatParticipation;
        return this;
    }

    public void setEtatParticipation(EtatParticipation etatParticipation) {
        this.etatParticipation = etatParticipation;
    }

    public String getAvis() {
        return avis;
    }

    public Participant avis(String avis) {
        this.avis = avis;
        return this;
    }

    public void setAvis(String avis) {
        this.avis = avis;
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
        Participant participant = (Participant) o;
        if (participant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), participant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Participant{" +
            "id=" + getId() +
            ", idMembre=" + getIdMembre() +
            ", idSoiree=" + getIdSoiree() +
            ", etatParticipation='" + getEtatParticipation() + "'" +
            ", avis='" + getAvis() + "'" +
            "}";
    }
}
