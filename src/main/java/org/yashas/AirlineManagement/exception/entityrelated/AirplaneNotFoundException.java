package org.yashas.AirlineManagement.exception.entityrelated;

public class AirplaneNotFoundException extends RuntimeException {
    
    public AirplaneNotFoundException(Long id) {
        super("Airplane with ID " + id + " not found");
    }
}
