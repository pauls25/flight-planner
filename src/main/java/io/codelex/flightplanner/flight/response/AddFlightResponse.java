package io.codelex.flightplanner.flight.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.airport.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;

import java.time.LocalDateTime;

public class AddFlightResponse {

    private Long id;
    private Airport from;
    private Airport to;
    private String carrier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    public AddFlightResponse(Flight flight) {
        this.id = flight.getId();
        this.from = flight.getFrom();
        this.to = flight.getTo();
        this.carrier = flight.getCarrier();
        this.departureTime = flight.getDepartureTime();
        this.arrivalTime = flight.getArrivalTime();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
