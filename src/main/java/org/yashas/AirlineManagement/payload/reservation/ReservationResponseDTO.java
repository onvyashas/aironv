package org.yashas.AirlineManagement.payload.reservation;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*  
 * DTO for returning reservation details in response to API requests.
 * Used for retrieving one or more reservations.
 * The flight ID is included as part of the reservation response.
 * Associated with GET /reservations/, GET /reservations/{id}, POST /reservations/, PATCH /reservations/{id} and GET /flights/{id}/reservations.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {

    private Long id;
    private String passengerName;
    private String passengerEmail;
    private String reservationCode;
    private boolean status;
    private LocalDateTime createdAt;
    private Long flightId;
}