package io.codelex.flightplanner.airport;

import io.codelex.flightplanner.airport.domain.Airport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AirportInMemoryService implements AirportService{
    private AirportInMemoryRepository airportInMemoryRepository;
    Logger logger = LoggerFactory.getLogger(AirportInMemoryService.class);

    public AirportInMemoryService(AirportInMemoryRepository airportInMemoryRepository) {
        this.airportInMemoryRepository = airportInMemoryRepository;
    }

    public Airport addAirport(Airport newAirport){
        boolean airportExists = false;
        for (Airport airport : airportInMemoryRepository.getAirports()) {
            if (airport.getAirport().equalsIgnoreCase(newAirport.getAirport())){
                airportExists = true;
                break;
            }
        }
        if ( ! airportExists){ airportInMemoryRepository.addAirport(newAirport);}
        return newAirport;
    }


    public List<Airport> searchAirports(String searchTerm) {
        List<Airport> airportSearchResults = new ArrayList<>();

        String searchString = searchTerm.replaceAll("\\s+", "").toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        Pattern pattern = Pattern.compile("\\b" + searchString);

        logger.info("Searching airports for " + searchString);

        for (Airport airport : airportInMemoryRepository.getAirports()) {

            Matcher airportMatcher = pattern.matcher(airport.getAirport().toLowerCase());
            Matcher countryMatcher = pattern.matcher(airport.getCountry().toLowerCase());
            Matcher cityMatcher = pattern.matcher(airport.getCity().toLowerCase());

            if (airportMatcher.find() || countryMatcher.find() || cityMatcher.find()){
                airportSearchResults.add(airport);

            }
        }
        return airportSearchResults;
    }

    public void clearAirports() {
        airportInMemoryRepository.clearAirports();
    }


}
