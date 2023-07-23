package io.codelex.flightplanner.airport.request;

import jakarta.validation.constraints.NotNull;

public class SearchAirportsRequest {
    @NotNull
    private String searchTerm;

    public SearchAirportsRequest(@NotNull String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
