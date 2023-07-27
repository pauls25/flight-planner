package io.codelex.flightplanner;

import io.codelex.flightplanner.airport.AirportInMemoryRepository;
import io.codelex.flightplanner.airport.domain.Airport;
import io.codelex.flightplanner.flight.FlightInMemoryRepository;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.AddFlightRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightPlannerApplicationTests {

	@Autowired
	AdminController adminController;

	@Autowired
	TestingController testingController;

	@Autowired
    FlightInMemoryRepository flightRepository;

	@Autowired
    AirportInMemoryRepository airportRepository;

	@Test
	public void addFlightsTest() {

		AddFlightRequest request = generateAddFlightRequest();
		adminController.addFlight(request);
		Flight savedFlight = flightRepository.getFlights().values().iterator().next();;

		Assertions.assertInstanceOf(Long.class, savedFlight.getId());
		Assertions.assertEquals(request.getFrom(), savedFlight.getFrom());
		Assertions.assertEquals(request.getTo(), savedFlight.getTo());
		Assertions.assertEquals(request.getCarrier(), savedFlight.getCarrier());
		Assertions.assertEquals(request.getDepartureTime(), savedFlight.getDepartureTime());
		Assertions.assertEquals(request.getArrivalTime(), savedFlight.getArrivalTime());
	}

	@Test
	public void clearFlightTest() {
		AddFlightRequest request = generateAddFlightRequest();
		adminController.addFlight(request);

		testingController.clearFlightsAndAirports();

		Assertions.assertTrue(flightRepository.getFlights().isEmpty());
		Assertions.assertTrue(airportRepository.getAirports().isEmpty());

	}

	public AddFlightRequest generateAddFlightRequest(){
		Airport fromAirport = new Airport("Latvia", "Riga", "RIX");
		Airport toAirport = new Airport("Sweden", "Stockholm", "ARN");
		String carrier = "Rynair";
		String departureTime = "2023-05-05 10:00";
		String arrivalTime = "2023-05-05 12:00";

		return new AddFlightRequest(fromAirport, toAirport, carrier, departureTime, arrivalTime);
	}
}

