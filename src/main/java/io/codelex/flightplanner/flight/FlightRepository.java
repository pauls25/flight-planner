package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.flight.domain.Flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f " +
            "WHERE f.from.airport ILIKE :from " +
            "AND f.to.airport ILIKE :to " +
            "AND substring(f.departureTime, 1, 10) ILIKE :departure_date")
    List<Flight> fetchFlightsByValues(@Param("from") String from,
                                      @Param("to") String to,
                                      @Param("departure_date") String departure_date);

}