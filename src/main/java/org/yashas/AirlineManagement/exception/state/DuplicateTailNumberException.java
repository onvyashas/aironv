package org.yashas.AirlineManagement.exception.state;

public class DuplicateTailNumberException extends RuntimeException {
    
    public DuplicateTailNumberException(String tailNumber) {
        super("Airplane with tail number " + tailNumber + " already exists.");
    }
}

