package org.yashas.AirlineManagement.util.constant;

public enum ReservationSuccess {
    
    RESERVATION_CREATED("Reservation created successfully and confirmation email sent."),
    RESERVATION_UPDATED("Reservation updated successfully and a new mail sent.");

    private final String message;

    ReservationSuccess(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}