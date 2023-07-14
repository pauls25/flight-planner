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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FlightService {
    private FlightRepository flightRepository;
    private AirportService airportService;
    private IdGenerator idGenerator;
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    public FlightService(FlightRepository flightRepository, AirportService airportService, IdGenerator idGenerator ){
        this.flightRepository = flightRepository;
        this.airportService = airportService;
        this.idGenerator = idGenerator;
    }

    public synchronized AddFlightResponse addFlight(AddFlightRequest flightRequest) {
        AddFlightResponse response = null;
        if (validateAddFlightRequest(flightRequest)){
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

            flightRepository.addFlight(id, newFlight);

            response = new AddFlightResponse(newFlight);
        }
        return response;

    }
    private boolean validateAddFlightRequest(AddFlightRequest flightRequest){

        boolean flightDoesntExist = validateFlight(flightRequest);
        boolean airportsAreValid =  validateRequestAirports(flightRequest);
        boolean datesAreValid = validateDates(flightRequest);

        return ( airportsAreValid & datesAreValid & flightDoesntExist);
    }

    private boolean validateFlight(AddFlightRequest addFlightRequest) {
        for (Flight flight : flightRepository.getFlights().values()) {
            if (
                    addFlightRequest.getTo().getAirport().trim().equalsIgnoreCase(flight.getTo().getAirport()) &&
                    addFlightRequest.getFrom().getAirport().trim().equalsIgnoreCase(flight.getFrom().getAirport()) &&
                    addFlightRequest.getDepartureTime().trim().equalsIgnoreCase(flight.getDepartureTime()) &&
                    addFlightRequest.getArrivalTime().trim().equalsIgnoreCase(flight.getArrivalTime())
            ){
                throw new FlightAlreadyAddedException("Flight already added: " + flight);
            }
        }
        return true;

    }

    public void clearFlight() {
        logger.info("Clearing flights!");
        flightRepository.clearFlights();
    }

    public synchronized GetFlightResponse fetchFlightById(Long flightId) {
        if (flightRepository.getFlights().keySet().contains(flightId)){
            return new GetFlightResponse(flightRepository.getFlights().get(flightId));
        } else {
            throw new FlightIdDoesntExistException("Flight doesn't exist for id: " + flightId);
        }
    }


    private Long generateId(){
        return idGenerator.getCurrentId();
    }

    public synchronized void deleteFlightByFlightId(String id) {
        Long flightId = Long.valueOf(id);

        if (flightRepository.getFlightIds().contains(flightId)){
            flightRepository.deleteFlightByFlightId(flightId);
            logger.info("Deleted flight from Repository id: " + id);
        }
        else {
            throw new NoFlightsWereDeletedException("Couldn't delete flight because Flight doesn't exist for id: " + id);
        }
    }

    public SearchFlightResponse searchFlightByValues(SearchFlightRequest request) {

        if (request.getTo().equals(request.getFrom())) {
            throw new MatchingAirportsException("To and from airports in SearchFlightsRequest match!");

        } else {
            logger.info("Searching for flights in request: " + request);
            List<Flight> flightsSearchResult = flightRepository.fetchFlightByValues(request);

            return new SearchFlightResponse(0, flightsSearchResult.size(), flightsSearchResult);

        }
    }

    public boolean validateDates(AddFlightRequest flightRequest){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime arrivalTime = LocalDateTime.parse(flightRequest.getArrivalTime(), dateTimeFormatter);
        LocalDateTime departureTime = LocalDateTime.parse(flightRequest.getDepartureTime(), dateTimeFormatter);

        if ( departureTime.equals(arrivalTime) || arrivalTime.isBefore(departureTime)) {
            logger.info(String.format("Dates %s and %s are invalid", flightRequest.getArrivalTime(), flightRequest.getDepartureTime()));
            throw new BadDateTimeException("DateTimes aren't valid " + arrivalTime + " " + departureTime);
        } else {
            return true;
        }
    }

    private boolean validateRequestAirports(AddFlightRequest flightRequest){

        String toAirport = flightRequest.getTo().getAirport().trim();
        String fromAirport = flightRequest.getFrom().getAirport().trim();

        if ( toAirport.equalsIgnoreCase(fromAirport))
        {
            logger.info("Airports match each other: " + flightRequest.getTo() + " " + flightRequest.getFrom());
            throw new MatchingAirportsException("To and From airports are the same!");
        } else {
            return true;
        }
    }

}
