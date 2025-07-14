package org.yashas.AirlineManagement.exception.state;

public class DuplicateFlightNumberException extends RuntimeException {
   
    public DuplicateFlightNumberException(String flightNumber ) {
        super("Flight with flight number " + flightNumber + " already exists.");
    }
}

