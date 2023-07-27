package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.AdminController;
import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.airport.domain.Airport;
import io.codelex.flightplanner.error.BadDateTimeException;
import io.codelex.flightplanner.error.FlightAlreadyAddedException;
import io.codelex.flightplanner.error.FlightIdDoesntExistException;
import io.codelex.flightplanner.error.MatchingAirportsException;
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
import java.util.Optional;


public class FlightDatabaseService implements FlightService{

    FlightRepository flightRepository;
    AirportService airportService;
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    public FlightDatabaseService(FlightRepository flightRepository, AirportService airportService) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
    }

    @Override
    public synchronized AddFlightResponse addFlight(AddFlightRequest flightRequest) {
        AddFlightResponse response = null;
        // TODO maybe throw only one type of exception by specify what went wrong in message.
        if (validateAddFlightRequest(flightRequest)){
            Airport fromAirport = airportService.findOrCreateAirport(
                    flightRequest.getFrom().getCountry(),
                    flightRequest.getFrom().getCity(),
                    flightRequest.getFrom().getAirport()
            );

            Airport toAirport = airportService.findOrCreateAirport(
                    flightRequest.getTo().getCountry(),
                    flightRequest.getTo().getCity(),
                    flightRequest.getTo().getAirport()
            );

            Flight newFlight = new Flight(
                    fromAirport,
                    toAirport,
                    flightRequest.getCarrier(),
                    flightRequest.getDepartureTime(),
                    flightRequest.getArrivalTime()
            );

            Flight addedFlight = flightRepository.save(newFlight);
            logger.info("Added flight from service to db Flight: " + newFlight);
            response = new AddFlightResponse(addedFlight);
        }
        // TODO don't return null. throw exception or return something else. check ts tests
        return response;
    }


    @Override
    public boolean validateAddFlightRequest(AddFlightRequest flightRequest)
    {
        boolean flightDoesntExist = validateFlight(flightRequest);
        boolean airportsAreValid =  validateRequestAirports(flightRequest);
        boolean datesAreValid = validateDates(flightRequest);

        return ( airportsAreValid & datesAreValid & flightDoesntExist);
    }

    @Override
    public boolean validateFlight(AddFlightRequest addFlightRequest) {
        // TODO ar streams
        for (Flight flight : flightRepository.findAll()) {
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

    @Override
    public void clearFlight() {
        // TODO check if airports are deleted as well, if not add delete airports
        flightRepository.deleteAll();
    }

    @Override
    public GetFlightResponse fetchFlightById(Long flightId) {
        Optional<Flight> foundFlight = flightRepository.findById(flightId);
        //TODO skatīt intellij ieteikumu
        if (foundFlight.isPresent()){
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

    // TODO should add documentation or comments on what is a value
    @Override
    public SearchFlightResponse searchFlightByValues(SearchFlightRequest request) {
        // TODO move to validation and call validation method?
        if (request.getTo().equals(request.getFrom())) {
            throw new MatchingAirportsException("To and from airports in SearchFlightsRequest match!");

        } else {
            logger.info("Searching for flights in request: " + request);

            String from = request.getFrom();
            String to = request.getTo();
            String departure_date = request.getDepartureDate();
            // TODO finish
            List<Flight> searchFlightsResult = flightRepository.fetchFlightByValues(from, to, departure_date);

            return new SearchFlightResponse(0, searchFlightsResult.size(), searchFlightsResult);

            // TODO implement pagination later
        }
    }

    // TODO Code duplication - consider moving to seperate class/package. now both methods need to be maintained
    @Override
    public boolean validateDates(AddFlightRequest flightRequest) {
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

    @Override
    public boolean validateRequestAirports(AddFlightRequest flightRequest) {
        // TODO salīdzināt pārējos laukus, jo tagad atšķirību nosaka tikai airport
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
