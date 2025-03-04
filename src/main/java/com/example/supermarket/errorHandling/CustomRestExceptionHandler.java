package com.example.supermarket.errorHandling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Lista per raccogliere tutti gli errori
        List<String> errors = new ArrayList<String>();

        // Per ogni errore si estrae il nome del campo ed il messaggio di default e si
        // concatenano
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        // Per ogni errore globale(non relativo ad un campo) si estraggono il nome
        // dell'oggetto e il relativo messaggio e si aggiungono
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        // Creo un oggetto ApiErrror e si restituisce la risposta
        // personalizzata
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {

        // Recupero il nome della classe dell'eccezione
        logger.info("Exception class " + ex.getClass().getName());

        // Compongo il messaggio di errore
        String errorMessage = ex.getClass().getName() + " Entity not found at " + request.getDescription(false);

        // Creo un oggetto ApiError e restituisco il messaggio personalizzato
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), errorMessage);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

    }

}
