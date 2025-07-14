package org.yashas.AirlineManagement.exception.state;

public class FlightFullyBookedException extends RuntimeException {

    public FlightFullyBookedException() {
        super("Flight capacity is full.");
    } 

    public FlightFullyBookedException(String message) {
        super(message);
    }
}