package org.yashas.AirlineManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yashas.AirlineManagement.payload.flight.FlightFilterRequestDTO;
import org.yashas.AirlineManagement.payload.flight.FlightRequestDTO;
import org.yashas.AirlineManagement.payload.flight.FlightResponseDTO;
import org.yashas.AirlineManagement.payload.flight.PartialFlightRequestDTO;
import org.yashas.AirlineManagement.payload.reservation.ReservationResponseDTO;
import org.yashas.AirlineManagement.service.interfaces.FlightService;
import org.yashas.AirlineManagement.service.interfaces.ReservationService;
import org.yashas.AirlineManagement.util.constant.FlightSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody; 
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final ReservationService reservationService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FlightResponseDTO>> getAllFlights() {
        List<FlightResponseDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<FlightResponseDTO> getFlightById(@PathVariable Long id) {
        FlightResponseDTO flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @GetMapping(value = "/{id}/reservations", produces = "application/json")
    public ResponseEntity<Object> getReservationsForFlight(@PathVariable Long id) {
        List<ReservationResponseDTO> reservations = reservationService.getReservationsByFlightId(id);
        if (reservations.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", FlightSuccess.NO_RESERVATIONS_FOR_FLIGHT.getMessage());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(reservations);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> createFlight(@RequestBody @Valid FlightRequestDTO flightRequestDTO) {
        FlightResponseDTO createdFlight = flightService.createFlight(flightRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", FlightSuccess.FLIGHT_CREATED.getMessage(),
                        "flight", createdFlight
                ));
    }

    @PostMapping("/filter")
    public List<FlightResponseDTO> searchFlights(@RequestBody FlightFilterRequestDTO flightFilterRequestDTO) {
        return flightService.getFilteredFlights(flightFilterRequestDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> partiallyUpdateFlight(
            @PathVariable Long id,
            @RequestBody @Valid PartialFlightRequestDTO flightRequestDTO) {
        FlightResponseDTO updatedFlight = flightService.partialUpdateFlight(id, flightRequestDTO);
        return ResponseEntity.ok(Map.of(
                "message", FlightSuccess.FLIGHT_UPDATED.getMessage(),
                "flight", updatedFlight
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok(FlightSuccess.FLIGHT_DELETED.getMessageWithId(id));
    }
}