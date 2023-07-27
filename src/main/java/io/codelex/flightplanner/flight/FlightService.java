package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.flight.request.AddFlightRequest;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import io.codelex.flightplanner.flight.response.AddFlightResponse;
import io.codelex.flightplanner.flight.response.GetFlightResponse;
import io.codelex.flightplanner.flight.response.SearchFlightResponse;

public interface FlightService {

    AddFlightResponse addFlight(AddFlightRequest flightRequest) ;

    boolean validateAddFlightRequest(AddFlightRequest flightRequest);

    boolean validateFlight(AddFlightRequest addFlightRequest) ;

    void clearFlight() ;

    GetFlightResponse fetchFlightById(Long flightId);

    void deleteFlightByFlightId(String id);

    SearchFlightResponse searchFlightByValues(SearchFlightRequest request);

    boolean validateDates(AddFlightRequest flightRequest);

    boolean validateRequestAirports(AddFlightRequest flightRequest);
}
