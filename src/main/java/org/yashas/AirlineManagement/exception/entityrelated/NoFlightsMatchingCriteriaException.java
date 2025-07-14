package org.yashas.AirlineManagement.exception.entityrelated;

public class NoFlightsMatchingCriteriaException extends RuntimeException {
    
    public NoFlightsMatchingCriteriaException() {
        super("No flights were found matching your search criteria.");
    }
    
    public NoFlightsMatchingCriteriaException(String message) {
        super(message);
    }
}
