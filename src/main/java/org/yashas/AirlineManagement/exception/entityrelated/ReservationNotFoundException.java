package org.yashas.AirlineManagement.exception.entityrelated;

public class ReservationNotFoundException extends RuntimeException {
   
    public ReservationNotFoundException(Long id) {
        super("Reservation not found with ID: " + id);
    }
}