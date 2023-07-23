package io.codelex.flightplanner.error;

public class FlightAlreadyAddedException extends RuntimeException{
    public FlightAlreadyAddedException(String message){
        super(message);
    }
}
