package io.codelex.flightplanner.error;

public class MatchingAirportsException extends RuntimeException{
    public MatchingAirportsException(String message){
        super(message);
    }
}
