package io.codelex.flightplanner.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AddFlightException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleAddFlightException(AddFlightException ex, WebRequest request){
        String message = ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({MatchingAirportsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMatchingAirportsException(MatchingAirportsException ex, WebRequest request){
        String message = ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({FlightRequestValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleFlightRequestValidationException(FlightRequestValidationException ex, WebRequest request){
        String message = ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String message = "not valid due to validation error: " + ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NoFlightsWereDeletedException.class})
    @ResponseStatus(HttpStatus.OK)
    protected ResponseEntity<Object> handleNoFlightsWereDeletedException(NoFlightsWereDeletedException ex, WebRequest request){

        String message = ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.OK, request);
    }

    @ExceptionHandler({FlightAlreadyAddedException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<Object> handleFlightAlreadyAddedException(FlightAlreadyAddedException ex, WebRequest request){

        String message = ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({FlightIdDoesntExistException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleFlightIdDoesntExistException(FlightIdDoesntExistException ex, WebRequest request){

        String message = ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
