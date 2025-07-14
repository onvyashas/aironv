package org.yashas.AirlineManagement.service.interfaces;

import java.util.List;
import org.yashas.AirlineManagement.payload.reservation.PartialReservationRequestDTO;
import org.yashas.AirlineManagement.payload.reservation.ReservationRequestDTO;
import org.yashas.AirlineManagement.payload.reservation.ReservationResponseDTO;

public interface ReservationService {
    
    List<ReservationResponseDTO> getAllReservations();
    
    ReservationResponseDTO getReservationById(Long id);
    
    List<ReservationResponseDTO> getReservationsByFlightId(Long flightId);
    
    ReservationResponseDTO createReservation(ReservationRequestDTO dto);
    
    ReservationResponseDTO partialUpdateReservation(Long id, PartialReservationRequestDTO reservationDTO);
}