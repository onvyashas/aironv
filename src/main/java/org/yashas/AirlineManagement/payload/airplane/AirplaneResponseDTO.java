package org.yashas.AirlineManagement.payload.airplane;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*  DTO for returning airplane details in response to API requests.
 *  Used for retrieving one or more airplanes.
 *  Associated with GET /airplanes/, GET /airplanes/{id}/, POST /airplanes/ and PATCH /airplanes/{id}/.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirplaneResponseDTO {
    private Long id;
    private String tailNumber;
    private String model;
    private Integer capacity;
    private Integer productionYear;
    private Boolean status;
}