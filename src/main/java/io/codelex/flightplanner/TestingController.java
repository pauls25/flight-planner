package io.codelex.flightplanner;

import io.codelex.flightplanner.airport.AirportInMemoryService;
import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.flight.FlightInMemoryService;
import io.codelex.flightplanner.flight.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingController {
    private FlightService flightService;
    private AirportService airportService;

    public TestingController(FlightService flightService, AirportService airportService){
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @PostMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clearFlightsAndAirports(){
        flightService.clearFlight();
        airportService.clearAirports();
    }
}
