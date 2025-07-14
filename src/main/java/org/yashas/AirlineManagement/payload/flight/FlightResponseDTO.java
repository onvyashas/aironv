package org.yashas.AirlineManagement.payload.flight;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for returning flight details in response to API requests.
 * Used for retrieving one or more flights.
 * The airplane ID is included as part of the flight response.
 * Associated with GET /flights/, GET /flights/{id}/, POST /flights/, PATCH /flights/{id}/ and GET /airplanes/{id}/flights.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponseDTO {
    private Long id;
    private String flightNumber;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Long airplaneId;
}