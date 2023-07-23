package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.airport.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.AddFlightRequest;
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
    AirportService airportService;
    @Mock
    FlightRepository flightRepository;

    @Mock
    IdGenerator idGenerator;

    @InjectMocks
    FlightService flightService;

    @Captor
    ArgumentCaptor<Flight> flightArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    @Test
    public void testAddFlight(){

        Airport fromAirport = new Airport("Latvia", "Riga", "RIX");
        Airport toAirport = new Airport("Sweden", "Stockholm", "ARN");
        String carrier = "Rynair";
        String departureTime = "2023-05-05 10:00";
        String arrivalTime = "2023-05-05 12:00";

        AddFlightRequest addFlightRequest = new AddFlightRequest(fromAirport, toAirport, carrier, departureTime, arrivalTime);

        flightService.addFlight(addFlightRequest);
        Mockito.verify(flightRepository).addFlight(longArgumentCaptor.capture(), flightArgumentCaptor.capture());

        Long addedId = longArgumentCaptor.getValue();
        Flight addedFlight = flightArgumentCaptor.getValue();

        Assertions.assertInstanceOf(Long.class, addedId);
        Assertions.assertEquals(addFlightRequest.getFrom(), addedFlight.getFrom());
        Assertions.assertEquals(addFlightRequest.getTo(), addedFlight.getTo());
        Assertions.assertEquals(addFlightRequest.getCarrier(), addedFlight.getCarrier());
        Assertions.assertEquals(addFlightRequest.getDepartureTime(), addedFlight.getDepartureTime());
        Assertions.assertEquals(addFlightRequest.getArrivalTime(), addedFlight.getArrivalTime());
    }

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
