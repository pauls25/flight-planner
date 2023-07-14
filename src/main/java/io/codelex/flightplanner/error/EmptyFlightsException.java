package io.codelex.flightplanner.error;

public class EmptyFlightsException extends RuntimeException {
    public EmptyFlightsException(String flightsAreEmpty) {
        super(flightsAreEmpty);
    }
}
