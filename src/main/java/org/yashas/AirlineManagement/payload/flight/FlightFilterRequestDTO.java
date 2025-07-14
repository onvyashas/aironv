package org.yashas.AirlineManagement.payload.flight;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for filtering flights based on below criterias.
 * Assosiated with POST /flights/filter endpoint.
 */
@Data
public class FlightFilterRequestDTO {

    @Schema(description = "Departure location (e.g. airport name)", example = "Charles de Gaulle Airport", required = true)
    private String departureLocation;

    @Schema(description = "Destination location (e.g. airport name)", example = "Istanbul Airport", required = true)
    private String arrivalLocation;

    @Schema(description = "Departure time of the flight", example = "2025-05-10T09:15:00", required = true)
    private LocalDate departureDate;

    @Schema(description = "Arrival time of the flight", example = "2025-05-10T12:45:00", required = true)
    private LocalDate arrivalDate;
}