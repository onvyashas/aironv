package org.yashas.AirlineManagement.payload.flight;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for partially updating flight details.
 * Fields can be null--> reason: allowing updates to only the specified attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartialFlightRequestDTO {

    @Schema(description = "Flight number", example = "TK105", required = false)
    private String flightNumber;

    @Schema(description = "Departure location (e.g. airport name)", example = "Heathrow Airport", required = false)
    private String departure;

    @Schema(description = "Destination location (e.g. airport name)", example = "Istanbul Airport", required = false)
    private String destination;
    
    @FutureOrPresent(message = "Departure time must be in the future or present.")
    @Schema(description = "Departure time of the flight", example = "2025-04-20T18:00:00", required = false)
    private LocalDateTime departureTime;

    @Schema(description = "Arrival time of the flight", example = "2025-04-21T18:00:00", required = false)
    private LocalDateTime arrivalTime;

    @Schema(description = "ID of the airplane assigned to the flight", example = "1", required = false)
    private Long airplaneId;
}