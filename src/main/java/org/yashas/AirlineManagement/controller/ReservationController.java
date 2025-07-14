package org.yashas.AirlineManagement.controller;

import java.util.List;
import java.util.Map;
import org.yashas.AirlineManagement.payload.reservation.PartialReservationRequestDTO;
import org.yashas.AirlineManagement.payload.reservation.ReservationRequestDTO;
import org.yashas.AirlineManagement.payload.reservation.ReservationResponseDTO;
import org.yashas.AirlineManagement.service.interfaces.ReservationService;
import org.yashas.AirlineManagement.util.constant.ReservationSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        List<ReservationResponseDTO> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        ReservationResponseDTO reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> createReservation(@RequestBody @Valid ReservationRequestDTO reservationRequestDTO) {
        ReservationResponseDTO createdReservation = reservationService.createReservation(reservationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", ReservationSuccess.RESERVATION_CREATED.getMessage(),
                        "reservation", createdReservation
                ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> partiallyUpdateReservation(
            @PathVariable Long id,
            @RequestBody @Valid PartialReservationRequestDTO reservationRequestDTO) {
        ReservationResponseDTO updatedReservation = reservationService.partialUpdateReservation(id, reservationRequestDTO);
        return ResponseEntity.ok(Map.of(
                "message", ReservationSuccess.RESERVATION_UPDATED.getMessage(),
                "reservation", updatedReservation
        ));
    }
}