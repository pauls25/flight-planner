package io.codelex.flightplanner.error;

public class NoFlightsWereDeletedException extends RuntimeException {
    public NoFlightsWereDeletedException(String message){
        super(message);
    }
}
