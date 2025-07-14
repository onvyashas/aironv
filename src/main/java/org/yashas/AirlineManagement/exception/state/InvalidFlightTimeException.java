package org.yashas.AirlineManagement.exception.state;

public class InvalidFlightTimeException extends RuntimeException {

    public InvalidFlightTimeException() {
        super("Arrival time must be after departure time."); 
    }

    public InvalidFlightTimeException(String message) {
        super(message); 
    }
}