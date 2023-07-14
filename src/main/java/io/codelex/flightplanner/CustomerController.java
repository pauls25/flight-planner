package io.codelex.flightplanner;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.airport.domain.Airport;
import io.codelex.flightplanner.flight.FlightService;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import io.codelex.flightplanner.flight.response.GetFlightResponse;
import io.codelex.flightplanner.flight.response.SearchFlightResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private FlightService flightService;
    private AirportService airportService;

    public CustomerController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @PostMapping("/flights/search")
    @ResponseStatus(HttpStatus.OK)
    public SearchFlightResponse searchFlight(@Valid @RequestBody SearchFlightRequest request){
        return flightService.searchFlightByValues(request);
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@NotBlank @RequestParam String search){
        return airportService.searchAirports(search);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetFlightResponse findFlightById(@PathVariable("id") String id){
        return flightService.fetchFlightById(Long.valueOf(id));
    }
}
