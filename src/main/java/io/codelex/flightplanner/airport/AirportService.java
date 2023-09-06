package io.codelex.flightplanner.airport;

import io.codelex.flightplanner.airport.domain.Airport;

import java.util.List;

public interface AirportService {

    Airport addAirport(Airport newAirport);

    List<Airport> searchAirports(String searchTerm) ;

    void clearAirports() ;

}
