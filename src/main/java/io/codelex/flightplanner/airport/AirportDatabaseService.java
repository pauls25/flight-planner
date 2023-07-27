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
    public void addAirport(Airport newAirport) {

        List<Airport> allAirports = airportRepository.findAll();
        logger.info("Found airports from database: " + allAirports);

        if (allAirports.size() ==0){
            airportRepository.save(newAirport);
            logger.info("added airports from service to db: " + newAirport);
        } else {
            boolean airportExists = false;
            for (Airport airport: allAirports){
                if (airport.getAirport().equalsIgnoreCase(newAirport.getAirport())){
                    airportExists = true;
                    logger.info("Found matching airports: " + airport.getAirport() + " " + newAirport.getAirport());
                    break;
                }
            }
            if (!airportExists) {
                airportRepository.save(newAirport);
                logger.info("added airports from service to db: " + newAirport);
            }
        }

    }

    public Airport findOrCreateAirport(String country, String city, String airportName) {
        Airport existingAirport = airportRepository.findByCountryAndCityAndAirport(country, city, airportName);
        if (existingAirport != null) {
            return existingAirport;
        } else {
            Airport newAirport = new Airport(country, city, airportName);
            return airportRepository.save(newAirport);
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
