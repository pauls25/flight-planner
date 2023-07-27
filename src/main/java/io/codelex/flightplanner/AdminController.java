package io.codelex.flightplanner;

import io.codelex.flightplanner.flight.FlightService;
import io.codelex.flightplanner.flight.request.AddFlightRequest;
import io.codelex.flightplanner.flight.response.AddFlightResponse;
import io.codelex.flightplanner.flight.response.GetFlightResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private FlightService flightService;
    public AdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public AddFlightResponse addFlight(@Valid @RequestBody AddFlightRequest flightRequest){
        return flightService.addFlight(flightRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public  GetFlightResponse fetchFlightById(@PathVariable("id") String id){
        return flightService.fetchFlightById(Long.valueOf(id));
    }

    @DeleteMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlightByFlightId(@PathVariable("id") String id){
        flightService.deleteFlightByFlightId(id);
    }


}
