package io.codelex.flightplanner.airport;

import io.codelex.flightplanner.airport.domain.Airport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AirportService {
    private AirportRepository airportRepository;
    Logger logger = LoggerFactory.getLogger(AirportService.class);
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public void addAirport(Airport newAirport){
        boolean airportExists = false;
        for (Airport airport : airportRepository.getAirports()) {
            if (airport.getAirport().equalsIgnoreCase(newAirport.getAirport())){
                airportExists = true;
                break;
            }
        }
        if ( ! airportExists){airportRepository.addAirport(newAirport);}
    }


    public List<Airport> searchAirports(String searchTerm) {
        List<Airport> airportSearchResults = new ArrayList<>();

        String searchString = searchTerm.replaceAll("\\s+", "").toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        Pattern pattern = Pattern.compile("\\b" + searchString);

        logger.info("Searching airports for " + searchString);

        for (Airport airport : airportRepository.getAirports()) {

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
        airportRepository.clearAirports();
    }

}
