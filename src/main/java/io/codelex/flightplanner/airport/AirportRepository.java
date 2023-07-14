package io.codelex.flightplanner.airport;

import io.codelex.flightplanner.airport.domain.Airport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class AirportRepository {
    private volatile CopyOnWriteArraySet<Airport> airportList = new CopyOnWriteArraySet<>();
    Logger logger = LoggerFactory.getLogger(AirportRepository.class);

    // bija synchornized
    public void addAirport(Airport newAirport) {

        airportList.add(newAirport);
        logger.info("Added airport to repository: "+ newAirport);
//        logger.info("Airport repository contents: "+ airportList.toString());
    }

    // bija synchornized
    public Set<Airport> getAirports() {
//        logger.info("Returning all airports!");
        return this.airportList;
    }

    public void clearAirports() {
        airportList.clear();
        logger.info("Cleared airports from AirportRepository!");
    }
}
