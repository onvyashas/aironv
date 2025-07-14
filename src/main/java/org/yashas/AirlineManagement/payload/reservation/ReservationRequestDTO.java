package org.yashas.AirlineManagement.payload.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a new reservation.
 * This class ensures input validation for mandatory fields.
 * Associated with POST /reservations/ endpoint.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {

    @NotBlank(message = "Passenger name is required")
    @Schema(description = "Full name of the passenger", example = "Emma Watson", required = true)
    private String passengerName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Passenger email is required")
    @Schema(description = "Email address of the passenger", example = "emma.watson@example.com", required = true)
    private String passengerEmail;

    @NotNull(message = "Flight ID is required")
    @Schema(description = "ID of the flight for which the reservation is made", example = "1", required = true)
    private Long flightId;
}