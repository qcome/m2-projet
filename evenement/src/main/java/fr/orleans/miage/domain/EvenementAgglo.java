package fr.orleans.miage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class EvenementAgglo extends Evenement{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @JsonProperty("uid")
    private Long uid;

    @JsonProperty("title")
    private String titre;

    @JsonProperty("placename")
    private String emplacement;

    @JsonProperty("city")
    private String ville;

    @JsonProperty("pricing_info")
    private String tarif;

    @JsonProperty("image")
    private String image;

    @Temporal(TemporalType.DATE)
    @JsonProperty("date_start")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @JsonProperty("date_end")
    private Date dateFin;

    @JsonProperty("space_time_info")
    private String infoLieuDate;

    @JsonProperty("department")
    private String departement;

    @JsonProperty("free_text")
    @Lob
    private String infos;

    @JsonProperty("address")
    private String adresse;

    @JsonProperty("image_thumb")
    private String imageVignette;

    @JsonProperty("region")
    private String region;

    @JsonProperty("tags")
    private String motsCles;

    @JsonProperty("description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("updated_at")
    private Date dateUpdate;

    @JsonProperty("timetable")
    @Transient
    private String horairesList;

    @JsonProperty("latlon")
    @Transient
    private List<Float> coordonneesList;

    @Embedded
    private Coordonnees coordonnees;

    public void addCoordonnees(){

        this.coordonnees = new Coordonnees(coordonneesList.get(0), coordonneesList.get(1));
    }

    public void addHoraires() throws ParseException {
        String[] horaires = this.horairesList.split(";");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        for(String horaire : horaires){
            String[] horaire_ = horaire.split("\\s+");
            this.addHoraire(new Horaire(format.parse(horaire_[0]), format.parse(horaire_[1]), this));
        }
    }

    public EvenementAgglo() {
    }

    public EvenementAgglo(String titre, String description, String adresse, String ville) {
        super(titre, description, adresse, ville);
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUid() { return uid; }

    public void setUid(Long uid) { this.uid = uid; }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getInfoLieuDate() {
        return infoLieuDate;
    }

    public void setInfoLieuDate(String infoLieuDate) {
        this.infoLieuDate = infoLieuDate;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getHorairesList() {
        return horairesList;
    }

    public void setHorairesList(String horairesList) throws ParseException {
        this.horairesList = horairesList;

    }

    public String getImageVignette() {
        return imageVignette;
    }

    public void setImageVignette(String imageVignette) {
        this.imageVignette = imageVignette;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMotsCles() {
        return motsCles;
    }

    public void setMotsCles(String motsCles) {
        this.motsCles = motsCles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Float> getCoordonneesList() { return coordonneesList; }

    public void setCoordonneesList(List<Float> coordonneesList) {
        this.coordonneesList = coordonneesList;
        addCoordonnees();
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public Date getDateUpdate() { return dateUpdate; }

    public void setDateUpdate(Date dateUpdate) { this.dateUpdate = dateUpdate; }

    @Override
    public String toString() {
        return "EvenementGlobal{" +
            "id=" + id +
            ", uid=" + uid +
            ", titre='" + titre + '\'' +
            ", emplacement='" + emplacement + '\'' +
            ", ville='" + ville + '\'' +
            ", tarif='" + tarif + '\'' +
            ", image='" + image + '\'' +
            ", dateDebut=" + dateDebut +
            ", dateFin=" + dateFin +
            ", infoLieuDate='" + infoLieuDate + '\'' +
            ", departement='" + departement + '\'' +
            ", infos='" + infos + '\'' +
            ", adresse='" + adresse + '\'' +
            ", imageVignette='" + imageVignette + '\'' +
            ", region='" + region + '\'' +
            ", motsCles='" + motsCles + '\'' +
            ", description='" + description + '\'' +
            ", dateUpdate=" + dateUpdate +
            ", horairesList='" + horairesList + '\'' +
            ", coordonneesList=" + coordonneesList +
            ", coordonnees=" + coordonnees +
            '}';
    }
}
