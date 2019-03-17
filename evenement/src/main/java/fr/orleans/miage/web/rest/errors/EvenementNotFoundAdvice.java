package fr.orleans.miage.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EvenementNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EvenementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String evenementNotFoundHandler(EvenementNotFoundException ex) {
        return ex.getMessage();
    }
}
