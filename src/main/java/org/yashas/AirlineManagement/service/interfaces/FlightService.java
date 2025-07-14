package org.yashas.AirlineManagement.service.interfaces;

import java.util.List;
import org.yashas.AirlineManagement.payload.flight.FlightFilterRequestDTO;
import org.yashas.AirlineManagement.payload.flight.FlightRequestDTO;
import org.yashas.AirlineManagement.payload.flight.FlightResponseDTO;
import org.yashas.AirlineManagement.payload.flight.PartialFlightRequestDTO;

public interface FlightService {
    
    List<FlightResponseDTO> getAllFlights();
    
    FlightResponseDTO getFlightById(Long id);
    
    List<FlightResponseDTO> getFlightsByAirplaneId(Long airplaneId);
    
    FlightResponseDTO createFlight(FlightRequestDTO flightDTO);
    
    List<FlightResponseDTO> getFilteredFlights(FlightFilterRequestDTO filter);
    
    FlightResponseDTO partialUpdateFlight(Long flightId, PartialFlightRequestDTO flightDTO);
    
    void deleteFlight(Long flightId);
}