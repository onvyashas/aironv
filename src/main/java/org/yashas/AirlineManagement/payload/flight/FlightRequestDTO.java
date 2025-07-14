package org.yashas.AirlineManagement.payload.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a new flight.
 * This class ensures input validation for mandatory fields.
 * Associated with POST /flights/ endpoint.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequestDTO {

    @NotBlank(message = "Flight number is required")
    @Schema(description = "Flight number", example = "TK105", required = true)
    private String flightNumber;

    @NotBlank(message = "Departure location is required")
    @Schema(description = "Departure location (e.g. airport name)", example = "Heathrow Airport", required = true)
    private String departure;

    @NotBlank(message = "Destination location is required")
    @Schema(description = "Destination location (e.g. airport name)", example = "Istanbul Airport", required = true)
    private String destination;
    
    @FutureOrPresent(message = "Departure time must be in the future or present.")
    @NotNull(message = "Deperature time is required")
    @Schema(description = "Departure time of the flight", example = "2025-04-20T18:00:00", required = true)
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Schema(description = "Arrival time of the flight", example = "2025-04-21T18:00:00", required = true)
    private LocalDateTime arrivalTime;

    @NotNull(message = "Airplane ID is required")
    @Schema(description = "ID of the airplane assigned to the flight", example = "1", required = true)
    private Long airplaneId;
}