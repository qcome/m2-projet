package fr.orleans.miage.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Soiree.
 */
@Entity
@Table(name = "soiree")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Soiree implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_membre_organisateur")
    private Long idMembreOrganisateur;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMembreOrganisateur() {
        return idMembreOrganisateur;
    }

    public Soiree idMembreOrganisateur(Long idMembreOrganisateur) {
        this.idMembreOrganisateur = idMembreOrganisateur;
        return this;
    }

    public void setIdMembreOrganisateur(Long idMembreOrganisateur) {
        this.idMembreOrganisateur = idMembreOrganisateur;
    }

    public String getNom() {
        return nom;
    }

    public Soiree nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Soiree description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Soiree soiree = (Soiree) o;
        if (soiree.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), soiree.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Soiree{" +
            "id=" + getId() +
            ", idMembreOrganisateur=" + getIdMembreOrganisateur() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
