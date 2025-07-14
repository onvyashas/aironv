package org.yashas.AirlineManagement.payload.reservation;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for partially updating reservation details.
 * Fields can be null--> reason: allowing updates to only the specified attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartialReservationRequestDTO {

    @Schema(description = "Full name of the passenger", example = "Emma Watson", required = false)
    private String passengerName;

    @Email(message = "Invalid email format")
    @Schema(description = "Email address of the passenger", example = "emma.watson@example.com", required = false)
    private String passengerEmail;

    @Schema(description = "ID of the flight for which the reservation is made", example = "1", required = false)
    private Long flightId;
}