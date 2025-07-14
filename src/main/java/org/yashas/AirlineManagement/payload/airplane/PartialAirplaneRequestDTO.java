package org.yashas.AirlineManagement.payload.airplane;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for partially updating airplane details.
 * Fields can be null--> reason: allowing updates to only the specified attributes.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class PartialAirplaneRequestDTO {

    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "Tail number must be alphanumeric")
    @Schema(description = "Tail number of the airplane", example = "TC-GGE", required = false)
    private String tailNumber;

    @Schema(description = "Model of the airplane", example = "Airbus A320", required = false)
    private String model;

    @Min(value = 1, message = "Capacity must be greater than 0")
    @Schema(description = "Passenger capacity of the airplane", example = "220", required = false)
    private Integer capacity;

    @Min(value = 1900, message = "Production year cannot be smaller than 1900")
    @Max(value = 2025, message = "Production year cannot be greater than 2025")
    @Schema(description = "Year the airplane was produced", example = "2010", required = false)
    private Integer productionYear;

    @Schema(description = "Availability status of the airplane (true = available, false = not available)", example = "true", required = false)
    private Boolean status;
}