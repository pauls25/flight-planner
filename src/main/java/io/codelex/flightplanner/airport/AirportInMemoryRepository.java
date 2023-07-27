package io.codelex.flightplanner.airport;

import io.codelex.flightplanner.airport.domain.Airport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class AirportInMemoryRepository {
    private volatile CopyOnWriteArraySet<Airport> airportList = new CopyOnWriteArraySet<>();
    Logger logger = LoggerFactory.getLogger(AirportInMemoryRepository.class);

    public void addAirport(Airport newAirport) {

        airportList.add(newAirport);
        logger.info("Added airport to repository: "+ newAirport);
    }

    public Set<Airport> getAirports() {
        return this.airportList;
    }

    public void clearAirports() {
        airportList.clear();
        logger.info("Cleared airports from AirportRepository!");
    }
}
