package io.codelex.flightplanner.airport;

import io.codelex.flightplanner.airport.domain.Airport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class AirportDatabaseService implements AirportService{

    AirportRepository airportRepository;

    Logger logger = LoggerFactory.getLogger(AirportInMemoryService.class);

    public AirportDatabaseService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Airport addAirport(Airport newAirport) {
        Airport existingAirport = airportRepository.findByCountryAndCityAndAirport(newAirport.getCountry(), newAirport.getCity(), newAirport.getAirport());

        if (existingAirport != null) {
            logger.info("Airport already exist: " + existingAirport);
            return existingAirport;
        } else {
            Airport airport = new Airport(newAirport.getCountry(), newAirport.getCity(), newAirport.getAirport());
            logger.info("Adding airport to repository: " + airport);
            return airportRepository.save(airport);
        }

    }

    @Override
    public List<Airport> searchAirports(String searchTerm) {
        return airportRepository.searchByFieldValues(searchTerm.toLowerCase().trim());
    }

    @Override
    public void clearAirports() {
        airportRepository.deleteAll();
    }
}
