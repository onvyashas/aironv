package org.yashas.AirlineManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yashas.AirlineManagement.payload.airplane.AirplaneRequestDTO;
import org.yashas.AirlineManagement.payload.airplane.AirplaneResponseDTO;
import org.yashas.AirlineManagement.payload.airplane.PartialAirplaneRequestDTO;
import org.yashas.AirlineManagement.payload.flight.FlightResponseDTO;
import org.yashas.AirlineManagement.service.interfaces.AirplaneService;
import org.yashas.AirlineManagement.service.interfaces.FlightService;
import org.yashas.AirlineManagement.util.constant.AirplaneSuccess;
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
@RequestMapping("/airplanes")
@RequiredArgsConstructor
public class AirplaneController {

    private final AirplaneService airplaneService;
    private final FlightService flightService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<AirplaneResponseDTO>> getAllAirplanes() {
        List<AirplaneResponseDTO> airplanes = airplaneService.getAllAirplanes();
        return ResponseEntity.ok(airplanes);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<AirplaneResponseDTO> getAirplaneById(@PathVariable Long id) {
        AirplaneResponseDTO airplane = airplaneService.getAirplaneById(id);
        return ResponseEntity.ok(airplane);
    }

    @GetMapping(value = "/{id}/flights", produces = "application/json")
    public ResponseEntity<Object> getFlightsForAirplane(@PathVariable Long id) {
        List<FlightResponseDTO> flights = flightService.getFlightsByAirplaneId(id);
        if (flights.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", AirplaneSuccess.NO_FLIGHTS_FOR_AIRPLANE.getMessage());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(flights);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> createAirplane(
            @RequestBody @Valid AirplaneRequestDTO airplaneRequestDTO) {
        AirplaneResponseDTO createdAirplane = airplaneService.createAirplane(airplaneRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(Map.of(
                                     "message", AirplaneSuccess.AIRPLANE_CREATED.getMessage(),
                                     "airplane", createdAirplane
                             ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> partiallyUpdateAirplane(
            @PathVariable Long id,
            @RequestBody @Valid PartialAirplaneRequestDTO airplaneRequestDTO) {
        AirplaneResponseDTO updatedAirplane = airplaneService.partialUpdateAirplane(id, airplaneRequestDTO);
        return ResponseEntity.ok(Map.of(
                "message", AirplaneSuccess.AIRPLANE_UPDATED.getMessage(),
                "airplane", updatedAirplane
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirplane(@PathVariable Long id) {
        airplaneService.deleteAirplane(id);
        return ResponseEntity.ok(AirplaneSuccess.AIRPLANE_DELETED.getMessageWithId(id));
    }
}