package fr.orleans.miage.web.rest.errors;

public class EvenementNotFoundException extends RuntimeException{
    public EvenementNotFoundException(Long id) {
        super("Evenement " + id + " non trouvé");
    }
}
