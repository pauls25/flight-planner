package io.codelex.flightplanner.configuration;

import io.codelex.flightplanner.airport.*;
import io.codelex.flightplanner.flight.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "flightplanner", name = "service.version", havingValue = "database")
    public AirportService getAirportServiceDatabaseVersion(AirportRepository airportRepository) {
        return new AirportDatabaseService(airportRepository);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flightplanner", name = "service.version", havingValue = "in-memory")
    public AirportService getAirportServiceInMemoryVersion(AirportInMemoryRepository airportInMemoryRepository) {
        return new AirportInMemoryService(airportInMemoryRepository);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flightplanner", name = "service.version", havingValue = "database")
    public FlightService getFlightServiceDatabaseVersion(FlightRepository flightRepository, AirportService airportDatabaseService) {
        return new FlightDatabaseService(flightRepository, airportDatabaseService);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flightplanner", name = "service.version", havingValue = "in-memory")
    public FlightService getFlightServiceInMemoryVersion(FlightInMemoryRepository flightInMemoryRepository, AirportService airportInMemoryService, IdGenerator idGenerator) {
        return new FlightInMemoryService(flightInMemoryRepository, airportInMemoryService, idGenerator);
    }

}
