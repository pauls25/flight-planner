package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;

import java.util.List;

public class SearchFlightResponse {
    private Integer page;
    private Integer totalItems;
    private List<Flight> items;

    public SearchFlightResponse(Integer page, Integer totalItems, List<Flight> items) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = items;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<Flight> getItems() {
        return items;
    }

    public void setItems(List<Flight> items) {
        this.items = items;
    }
}
