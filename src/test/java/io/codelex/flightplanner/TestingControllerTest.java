package io.codelex.flightplanner;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.flight.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TestingControllerTest {

    @Mock
    FlightService flightService;
    @Mock
    AirportService airportService;

    @InjectMocks
    TestingController testingController;
    @Test
    void clearFlightsAndAirports() {
        testingController.clearFlightsAndAirports();
        Mockito.verify(flightService).clearFlight();
        Mockito.verify(airportService).clearAirports();
    }
}