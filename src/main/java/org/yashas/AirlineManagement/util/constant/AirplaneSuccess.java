package org.yashas.AirlineManagement.util.constant;

public enum AirplaneSuccess {
    
    AIRPLANE_CREATED("Airplane successfully created."),
    AIRPLANE_UPDATED("Airplane successfully updated."),
    AIRPLANE_DELETED("Airplane successfully deleted."),
    NO_FLIGHTS_FOR_AIRPLANE("There are no flights for this airplane yet.");

    private final String message;

    AirplaneSuccess(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageWithId(Long id) {
        return message + " Airplane ID: " + id;
    }
}