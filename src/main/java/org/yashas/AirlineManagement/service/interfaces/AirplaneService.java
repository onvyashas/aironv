package org.yashas.AirlineManagement.service.interfaces;

import java.util.List;
import org.yashas.AirlineManagement.payload.airplane.AirplaneRequestDTO;
import org.yashas.AirlineManagement.payload.airplane.AirplaneResponseDTO;
import org.yashas.AirlineManagement.payload.airplane.PartialAirplaneRequestDTO;

public interface AirplaneService {
    
    List<AirplaneResponseDTO> getAllAirplanes();
    
    AirplaneResponseDTO getAirplaneById(Long id);
    
    AirplaneResponseDTO createAirplane(AirplaneRequestDTO airplaneDTO);
    
    AirplaneResponseDTO partialUpdateAirplane(Long id, PartialAirplaneRequestDTO airplaneDTO);
    
    void deleteAirplane(Long airplaneId);
}