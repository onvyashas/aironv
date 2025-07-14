package org.yashas.AirlineManagement.util.constant;

public enum FlightSuccess {
    
    FLIGHT_CREATED("Flight successfully created."),
    FLIGHT_UPDATED("Flight successfully updated."),
    FLIGHT_DELETED("Flight successfully deleted."),
    NO_RESERVATIONS_FOR_FLIGHT("There are no reservations for this flight yet.");

    private final String message;

    FlightSuccess(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageWithId(Long id) {
        return message + " Flight ID: " + id;
    }
}