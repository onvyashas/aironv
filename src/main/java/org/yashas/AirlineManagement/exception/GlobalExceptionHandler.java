package org.yashas.AirlineManagement.exception;

import org.yashas.AirlineManagement.exception.entityrelated.AirplaneNotFoundException;
import org.yashas.AirlineManagement.exception.entityrelated.FlightNotFoundException;
import org.yashas.AirlineManagement.exception.entityrelated.NoFlightsMatchingCriteriaException;
import org.yashas.AirlineManagement.exception.entityrelated.ReservationNotFoundException;
import org.yashas.AirlineManagement.exception.state.AirplaneNotAvailableException;
import org.yashas.AirlineManagement.exception.state.DuplicateFlightNumberException;
import org.yashas.AirlineManagement.exception.state.DuplicateTailNumberException;
import org.yashas.AirlineManagement.exception.state.FlightFullyBookedException;
import org.yashas.AirlineManagement.exception.state.InvalidFlightTimeException;
import org.yashas.AirlineManagement.exception.state.NoChangesMadeException;
import org.yashas.AirlineManagement.payload.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle AirplaneNotAvailableException and return a structured response.
     * Responds with a 400 Bad Request status and the exception message.
     */
    @ExceptionHandler(AirplaneNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleAirplaneNotAvailable(AirplaneNotAvailableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Airplane Not Available",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle InvalidFlightTimeException and return a structured response.
     * Responds with a 400 Bad Request status and the exception message.
     */
    @ExceptionHandler(InvalidFlightTimeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFlightTime(InvalidFlightTimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Flight Time",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle FlightFullyBookedException and return a structured response.
     * Responds with a 400 Bad Request status and the exception message.
     */
    @ExceptionHandler(FlightFullyBookedException.class)
    public ResponseEntity<ErrorResponse> handleFlightFullyBookedException(FlightFullyBookedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Flight Fully Booked",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle DuplicateFlightNumberException and return a structured response.
     * Responds with a 409 Conflict status and the exception message.
     */
    @ExceptionHandler(DuplicateFlightNumberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateFlightNumberException(DuplicateFlightNumberException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Flight Number Already In Use.",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Handle DuplicateTailNumberException and return a structured response.
     * Responds with a 409 Conflict status and the exception message.
     */
    @ExceptionHandler(DuplicateTailNumberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTailNumberException(DuplicateTailNumberException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Tail Number Already In Use.",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Handle NoChangesMadeException and return a structured response.
     * Responds with a 409 Conflict status if no actual changes were made.
     */
    @ExceptionHandler(NoChangesMadeException.class)
    public ResponseEntity<ErrorResponse> handleNoChangesMadeException(NoChangesMadeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "No Changes Made",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
    /**
     * Handle NoFlightsMatchingCriteriaException and return a structured response.
     * Responds with a 404 Not Found status and the exception message.
     */
    @ExceptionHandler(NoFlightsMatchingCriteriaException.class)
    public ResponseEntity<ErrorResponse> handleNoFlightsMatchingCriteriaException(NoFlightsMatchingCriteriaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "No Matching Flights Found",
            ex.getMessage(),
            LocalDateTime.now()
        ); 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handle FlightNotFoundException and return a structured response.
     * Responds with a 404 Not Found status and the exception message.
     */
    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFlightNotFoundException(FlightNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Flight Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handle AirplaneNotFoundException and return a structured response.
     * Responds with a 404 Not Found status and the exception message.
     */
    @ExceptionHandler(AirplaneNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAirplaneNotFoundException(AirplaneNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Airplane Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handle ReservationNotFoundException and return a structured response.
     * Responds with a 404 Not Found status and the exception message.
     */
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReservationNotFoundException(ReservationNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Reservation Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handle validation errors (MethodArgumentNotValidException).
     * Responds with a 400 Bad Request status and a list of validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                String.join(", ", validationErrors),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handle all other exceptions (generic error handling).
     * Responds with a 500 Internal Server Error status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}