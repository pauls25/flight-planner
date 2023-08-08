package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.AdminController;
import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.error.*;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.AddFlightRequest;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import io.codelex.flightplanner.flight.response.AddFlightResponse;
import io.codelex.flightplanner.flight.response.GetFlightResponse;
import io.codelex.flightplanner.flight.response.SearchFlightResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class FlightInMemoryService implements FlightService {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    private FlightInMemoryRepository flightInMemoryRepository;
    private AirportService airportService;
    private IdGenerator idGenerator;

    public FlightInMemoryService(FlightInMemoryRepository flightInMemoryRepository, AirportService airportService, IdGenerator idGenerator) {
        this.flightInMemoryRepository = flightInMemoryRepository;
        this.airportService = airportService;
        this.idGenerator = idGenerator;
    }

    public synchronized AddFlightResponse addFlight(AddFlightRequest flightRequest) {
        if (validateAddFlightRequest(flightRequest)) {
            Long id = generateId();
            Flight newFlight = new Flight(
                    id,
                    flightRequest.getFrom(),
                    flightRequest.getTo(),
                    flightRequest.getCarrier(),
                    flightRequest.getDepartureTime(),
                    flightRequest.getArrivalTime()
            );

            airportService.addAirport(flightRequest.getTo());
            airportService.addAirport(flightRequest.getFrom());

            Flight addedFlight = flightInMemoryRepository.addFlight(newFlight);

            return new AddFlightResponse(addedFlight);
        } else {
            throw new AddFlightException("Failed to add flight for request: " + flightRequest);
        }
    }

    public boolean validateAddFlightRequest(AddFlightRequest flightRequest) {

        boolean flightDoesntExist = validateFlight(flightRequest);
        boolean airportsAreValid = validateRequestAirports(flightRequest);
        boolean datesAreValid = validateDates(flightRequest);

        return (airportsAreValid && datesAreValid && flightDoesntExist);
    }

    public boolean validateFlight(AddFlightRequest addFlightRequest) {

        for (Flight flight : flightInMemoryRepository.getFlights().values()) {
            if (
                    addFlightRequest.getTo().getAirport().trim().equalsIgnoreCase(flight.getTo().getAirport()) &&
                            addFlightRequest.getFrom().getAirport().trim().equalsIgnoreCase(flight.getFrom().getAirport()) &&
                            addFlightRequest.getDepartureTime().trim().equalsIgnoreCase(flight.getDepartureTime()) &&
                            addFlightRequest.getArrivalTime().trim().equalsIgnoreCase(flight.getArrivalTime())
            ) {
                throw new FlightAlreadyAddedException("Flight already added: " + flight);
            }
        }
        return true;
    }

    public void clearFlight() {
        logger.info("Clearing flights!");
        flightInMemoryRepository.clearFlights();
    }

    public synchronized GetFlightResponse fetchFlightById(Long flightId) {
        if (flightInMemoryRepository.getFlights().keySet().contains(flightId)) {
            return new GetFlightResponse(flightInMemoryRepository.getFlights().get(flightId));
        } else {
            throw new FlightIdDoesntExistException("Flight doesn't exist for id: " + flightId);
        }
    }

    private Long generateId() {
        return idGenerator.getCurrentId();
    }

    public synchronized void deleteFlightByFlightId(String id) {
        Long flightId = Long.valueOf(id);

        if (flightInMemoryRepository.getFlightIds().contains(flightId)) {

            flightInMemoryRepository.deleteFlightByFlightId(flightId);
            logger.info("Deleted flight from Repository id: " + id);
        } else {
            throw new NoFlightsWereDeletedException("Couldn't delete flight because Flight doesn't exist for id: " + id);
        }
    }

    public SearchFlightResponse searchFlightByValues(SearchFlightRequest request) {

        if (request.getTo().equals(request.getFrom())) {
            throw new MatchingAirportsException("To and from airports in SearchFlightsRequest match!");

        } else {
            logger.info("Searching for flights in request: " + request);
            List<Flight> flightsSearchResult = flightInMemoryRepository.fetchFlightByValues(request);

            return new SearchFlightResponse(0, flightsSearchResult.size(), flightsSearchResult);

        }
    }

    public boolean validateDates(AddFlightRequest flightRequest) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime arrivalTime = LocalDateTime.parse(flightRequest.getArrivalTime(), dateTimeFormatter);
        LocalDateTime departureTime = LocalDateTime.parse(flightRequest.getDepartureTime(), dateTimeFormatter);

        if (departureTime.equals(arrivalTime) || arrivalTime.isBefore(departureTime)) {
            logger.info(String.format("Dates %s and %s are invalid", flightRequest.getArrivalTime(), flightRequest.getDepartureTime()));
            throw new FlightRequestValidationException("DateTimes aren't valid " + arrivalTime + " " + departureTime);
        } else {
            return true;
        }
    }

    public boolean validateRequestAirports(AddFlightRequest flightRequest) {

        String toAirport = flightRequest.getTo().getAirport().trim();
        String fromAirport = flightRequest.getFrom().getAirport().trim();

        if (toAirport.equalsIgnoreCase(fromAirport)) {
            logger.info("Airports match each other: " + flightRequest.getTo() + " " + flightRequest.getFrom());
            throw new FlightRequestValidationException("To and From airports are the same!");
        } else {
            return true;
        }
    }

}
