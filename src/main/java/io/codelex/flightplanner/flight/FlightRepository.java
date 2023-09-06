package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.airport.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("select f from Flight f " +
            "where f.from.airport = :from " +
            "and f.to.airport = :to " +
            "and DATE(f.departureTime) = :departure_date")
    List<Flight> fetchFlightsByValues(@Param("from") String from,
                                      @Param("to") String to,
                                      @Param("departure_date") LocalDate departure_date);

    Optional<Flight> findByFromAndToAndCarrierAndArrivalTimeAndDepartureTime(Airport from, Airport to, String carrier, LocalDateTime arrivalTime, LocalDateTime departureTime);
}