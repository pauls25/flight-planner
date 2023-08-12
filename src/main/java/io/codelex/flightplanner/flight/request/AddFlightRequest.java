package io.codelex.flightplanner.flight.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.airport.domain.Airport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AddFlightRequest {

    @Valid
    @NotNull
    private Airport from;

    @Valid
    @NotNull
    private Airport to;

    @NotBlank
    private String carrier;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;


    public AddFlightRequest(Airport from, Airport to, String carrier,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime departureTime,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
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

    @Override
    public String toString() {
        return "AddFlightRequest{" +
                "carrier='" + carrier + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}
