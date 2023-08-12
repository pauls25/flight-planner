package io.codelex.flightplanner.flight.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SearchFlightRequest {
    @NotNull
    private String from;
    @NotNull
    private String to;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    public SearchFlightRequest(@NotNull String from, @NotNull String to, @NotNull LocalDate datetime) {
        this.from = from;
        this.to = to;
        this.departureDate = datetime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public String toString() {
        return "SearchFlightRequest{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departureDate='" + departureDate + '\'' +
                '}';
    }
}
