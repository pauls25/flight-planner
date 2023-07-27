package io.codelex.flightplanner.airport;

import io.codelex.flightplanner.airport.domain.Airport;

import java.util.List;

public interface AirportService {

    void addAirport(Airport newAirport);

    List<Airport> searchAirports(String searchTerm) ;

    void clearAirports() ;

    // TODO try to get rid of this otherwise InMemory version has unused method. try with searchAirport() above
    Airport findOrCreateAirport(String country, String city, String airportName);

}
