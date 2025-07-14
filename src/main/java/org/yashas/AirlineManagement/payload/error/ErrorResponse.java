package org.yashas.AirlineManagement.payload.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Represents a standardized error response for API exceptions.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}