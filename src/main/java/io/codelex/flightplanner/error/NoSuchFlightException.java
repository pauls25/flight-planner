package io.codelex.flightplanner.error;

public class NoSuchFlightException extends RuntimeException{
    public NoSuchFlightException(String flightsAreEmpty) {
        super(flightsAreEmpty);
    }
}
