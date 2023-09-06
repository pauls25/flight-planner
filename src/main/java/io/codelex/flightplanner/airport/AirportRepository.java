package io.codelex.flightplanner.airport;

import io.codelex.flightplanner.airport.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Integer> {

    Airport findByCountryAndCityAndAirport(String country, String city, String airportName);

    @Query("SELECT a FROM Airport a " +
            "WHERE trim(lower(a.airport)) like (:searchTerm || '%')" +
            "OR trim(lower(a.city)) like (:searchTerm || '%')" +
            "OR trim(lower(a.country)) like (:searchTerm || '%')")
    List<Airport> searchByFieldValues(@Param("searchTerm") String searchTerm);

}
