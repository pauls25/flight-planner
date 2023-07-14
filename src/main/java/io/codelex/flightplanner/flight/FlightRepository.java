package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.error.*;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FlightRepository {

    private volatile  Map<Long, Flight> flights = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(FlightRepository.class);

    public synchronized void addFlight(Long id, Flight flight) {
        flights.put(id, flight);
        logger.info("Added flight to FlightRepository at id: " + id + " " + flight);
    }

    public void clearFlights() {
        flights.clear();
        logger.info("FlightRepository was cleared");
    }

    public synchronized Set<Long> getFlightIds(){
        return flights.keySet();
    }

    public synchronized Flight fetchFlightById(Long flightId) {
        if (flights.containsKey(flightId)){

            logger.info("Found Flight id: " + flightId);
            return flights.get(flightId);
        } else {
            throw new FlightIdDoesntExistException("Flight doesn't exist for id: " + flightId);
        }
    }


    public synchronized void deleteFlightByFlightId(Long id) {
        logger.info("Deleted flight from Repository id: " + id);
        flights.remove(id);
    }

    public synchronized List<Flight> fetchFlightByValues(SearchFlightRequest request){
        List<Flight> searchResults = new ArrayList<>();

        for (Flight flight : flights.values()) {

            String flightDepartureDate = flight.getDepartureTime().substring(0, 11).trim();
            if (
                request.getTo().trim().equalsIgnoreCase(flight.getTo().getAirport()) &&
                request.getFrom().trim().equalsIgnoreCase(flight.getFrom().getAirport()) &&
                request.getDepartureDate().trim().equals(flightDepartureDate)
            ){
                searchResults.add(flight);
            }
        }

        return searchResults;
    }
    public Map<Long, Flight> getFlights() {
        return flights;
//        return new ArrayList<>(flights.values());
    }

}