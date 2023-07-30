package io.codelex.flightplanner.flight.request;

import io.codelex.flightplanner.airport.domain.Airport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddFlightRequest {

    @Valid
    @NotNull
    private Airport from;
    @Valid
    @NotNull
    private Airport to;
    @NotBlank
    private String carrier;

    @NotBlank
    private String departureTime;

    @NotBlank
    private String arrivalTime;


    public AddFlightRequest( Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
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
