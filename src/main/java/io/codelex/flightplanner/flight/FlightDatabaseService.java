package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.airport.domain.Airport;
import io.codelex.flightplanner.error.*;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.AddFlightRequest;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import io.codelex.flightplanner.flight.response.AddFlightResponse;
import io.codelex.flightplanner.flight.response.GetFlightResponse;
import io.codelex.flightplanner.flight.response.SearchFlightResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class FlightDatabaseService implements FlightService {

    private final FlightRepository flightRepository;
    private final AirportService airportService;
    Logger logger = LoggerFactory.getLogger(FlightDatabaseService.class);

    public FlightDatabaseService(FlightRepository flightRepository, AirportService airportService) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
    }

    @Override
    public synchronized AddFlightResponse addFlight(AddFlightRequest flightRequest) {
        logger.debug("Got request: " + flightRequest);
        if (validateAddFlightRequest(flightRequest)) {

            Airport fromAirport = airportService.addAirport(flightRequest.getFrom());
            Airport toAirport = airportService.addAirport(flightRequest.getTo());

            Flight newFlight = new Flight(fromAirport, toAirport, flightRequest.getCarrier(), flightRequest.getDepartureTime(), flightRequest.getArrivalTime());

            Flight addedFlight = flightRepository.save(newFlight);
            logger.info("Added flight from service to db Flight: " + newFlight);
            return new AddFlightResponse(addedFlight);
        } else {
            throw new AddFlightException("Failed to add new Flight with request: " + flightRequest);
        }
    }


    @Override
    public boolean validateAddFlightRequest(AddFlightRequest flightRequest) {
        boolean flightDoesntExist = validateFlight(flightRequest);
        boolean airportsAreValid = validateRequestAirports(flightRequest);
        boolean datesAreValid = validateDates(flightRequest);

        return (airportsAreValid && datesAreValid && flightDoesntExist);
    }

    @Override
    public boolean validateFlight(AddFlightRequest addFlightRequest) {

        Airport fromAirport = airportService.addAirport(addFlightRequest.getFrom());
        Airport toAirport = airportService.addAirport(addFlightRequest.getTo());
        Optional<Flight> foundFlight = flightRepository.findByFromAndToAndCarrierAndArrivalTimeAndDepartureTime(
                fromAirport,
                toAirport,
                addFlightRequest.getCarrier(),
                addFlightRequest.getArrivalTime(),
                addFlightRequest.getDepartureTime()
        );

        if (foundFlight.isPresent()){
            throw new FlightAlreadyAddedException("Flight already added: " + foundFlight);
        }

        logger.debug("Validated flight: " + addFlightRequest);
        return true;
    }

    @Override
    public void clearFlight() {
        flightRepository.deleteAll();
    }

    @Override
    public GetFlightResponse fetchFlightById(Long flightId) {
        Optional<Flight> foundFlight = flightRepository.findById(flightId);

        if (foundFlight.isPresent()) {
            return new GetFlightResponse(foundFlight.get());
        } else {
            throw new FlightIdDoesntExistException("Flight doesn't exist for id: " + flightId);
        }
    }

    @Override
    public synchronized void deleteFlightByFlightId(String id) {
        flightRepository.deleteById(Long.valueOf(id));
        logger.info("Deleted flight from database by Id: " + id);
    }

    @Override
    public SearchFlightResponse searchFlightByValues(SearchFlightRequest request) {
        if (request.getTo().equals(request.getFrom())) {
            throw new MatchingAirportsException("To and from airports in SearchFlightsRequest match!");

        } else {
            logger.info("Searching for flights in request: " + request);

            String from = request.getFrom();
            String to = request.getTo();
            LocalDate departure_date = request.getDepartureDate();

            List<Flight> searchFlightsResult = flightRepository.fetchFlightsByValues(from, to, departure_date);

            return new SearchFlightResponse(0, searchFlightsResult.size(), searchFlightsResult);
        }
    }

    @Override
    public boolean validateDates(AddFlightRequest flightRequest) {

        if (flightRequest.getDepartureTime().equals(flightRequest.getArrivalTime())) {

            logger.info(String.format("Dates %s and %s are invalid", flightRequest.getArrivalTime(), flightRequest.getDepartureTime()));
            throw new FlightRequestValidationException("Arrival and Departure date time is the same: " + flightRequest.getArrivalTime() + " " + flightRequest.getDepartureTime());

        } else if (flightRequest.getArrivalTime().isBefore(flightRequest.getDepartureTime())) {

            logger.info(String.format("Dates %s and %s are invalid", flightRequest.getArrivalTime(), flightRequest.getDepartureTime()));
            throw new FlightRequestValidationException("Arrival date time is before departure date time: " + flightRequest.getArrivalTime() + " " + flightRequest.getDepartureTime());
        } else {
            return true;
        }
    }

    @Override
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
