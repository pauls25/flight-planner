package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.airport.AirportInMemoryService;
import io.codelex.flightplanner.airport.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import io.codelex.flightplanner.flight.response.GetFlightResponse;
import io.codelex.flightplanner.flight.response.SearchFlightResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {
    @Mock
    AirportInMemoryService airportService;
    @Mock
    FlightInMemoryRepository flightRepository;

    @Mock
    IdGenerator idGenerator;

    @InjectMocks
    FlightInMemoryService flightService;


    @Test
    public void fetchFlightById() {
        Long id = 0L;
        Airport fromAirport = new Airport("Latvia", "Riga", "RIX");
        Airport toAirport = new Airport("Sweden", "Stockholm", "ARN");
        String carrier = "Rynair";
        String departureTime = "2023-05-05 10:00";
        String arrivalTime = "2023-05-05 12:00";

        Flight expectedFlight = new Flight(id, fromAirport, toAirport, carrier, departureTime, arrivalTime);
        Map<Long, Flight> flights= new HashMap<>();
        flights.put(id, expectedFlight);


        Mockito.when(flightRepository.getFlights()).thenReturn(flights);

        GetFlightResponse expectedGetFlightResponse = new GetFlightResponse(expectedFlight);
        GetFlightResponse receivedGetFlightResponse = flightService.fetchFlightById(id);

        Assertions.assertEquals(receivedGetFlightResponse.getId(), id);
        Assertions.assertEquals(expectedGetFlightResponse, receivedGetFlightResponse);
    }

    @Test
    public void testSearchFlightByValues(){
        SearchFlightRequest searchRequest = new SearchFlightRequest("RIX", "ARN", "2023-05-05" );

        Airport fromAirport = new Airport("Latvia", "Riga", "RIX");
        Airport toAirport = new Airport("Sweden", "Stockholm", "ARN");
        String carrier = "Rynair";
        String departureTime = "2023-05-05 10:00";
        String arrivalTime = "2023-05-05 12:00";

        Flight flight = new Flight(0L, fromAirport, toAirport, carrier, departureTime, arrivalTime);
        List<Flight> expectedSearchResults= new ArrayList<>();
        expectedSearchResults.add(flight);

        Mockito.when(flightRepository.fetchFlightByValues(searchRequest)).thenReturn(expectedSearchResults);
        SearchFlightResponse response = flightService.searchFlightByValues(searchRequest);

        Assertions.assertEquals(0,response.getPage());
        Assertions.assertEquals(1,response.getTotalItems());
        Assertions.assertEquals(expectedSearchResults,response.getItems());

    }
}
