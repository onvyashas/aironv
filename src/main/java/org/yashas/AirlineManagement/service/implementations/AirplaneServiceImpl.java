package org.yashas.AirlineManagement.service.implementations;

import org.yashas.AirlineManagement.exception.entityrelated.AirplaneNotFoundException;
import org.yashas.AirlineManagement.exception.state.DuplicateTailNumberException;
import org.yashas.AirlineManagement.exception.state.NoChangesMadeException;
import org.yashas.AirlineManagement.mapper.AirplaneMapper;
import org.yashas.AirlineManagement.model.Airplane;
import org.yashas.AirlineManagement.payload.airplane.AirplaneRequestDTO;
import org.yashas.AirlineManagement.payload.airplane.AirplaneResponseDTO;
import org.yashas.AirlineManagement.payload.airplane.PartialAirplaneRequestDTO;
import org.yashas.AirlineManagement.repository.AirplaneRepository;
import org.yashas.AirlineManagement.service.interfaces.AirplaneService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirplaneServiceImpl implements AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final AirplaneMapper airplaneMapper;

    /**
     * Retrieves a list of all airplanes as DTOs.
     * 
     * @return list of airplane response DTOs, or an empty list if none are found
     */
    @Override
    @Transactional(readOnly = true)
    public List<AirplaneResponseDTO> getAllAirplanes() {
        return airplaneRepository.findAll()
                .stream()
                .map(airplaneMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific airplane by its unique ID.
     * 
     * @param id ID of the airplane to retrieve
     * @return airplane response DTO corresponding to the given ID
     * @throws AirplaneNotFoundException if no airplane with the given ID is found
     */
    @Override
    @Transactional(readOnly = true)
    public AirplaneResponseDTO getAirplaneById(Long id) {
        return airplaneRepository.findById(id)
                .map(airplaneMapper::toResponse)
                .orElseThrow(() -> new AirplaneNotFoundException(id));
    }

    /**
     * Creates a new airplane entry in the repository.
     * 
     * @param airplaneDTO airplane details for creation
     * @return the created airplane response DTO
     * @throws DuplicateTailNumberException if the tail number is already assigned
     * @throws DataIntegrityViolationException if data integrity constraints are violated
     * @throws RuntimeException for unexpected errors during airplane creation
     */
    @Override
    @Transactional
    public AirplaneResponseDTO createAirplane(AirplaneRequestDTO airplaneDTO) {
        try {

            if (airplaneRepository.existsByTailNumber(airplaneDTO.getTailNumber())) {
                throw new DuplicateTailNumberException(airplaneDTO.getTailNumber());
            }

            Airplane airplane = airplaneMapper.toEntity(airplaneDTO);
            Airplane savedAirplane = airplaneRepository.save(airplane);

            log.info("Airplane successfully created: {}", savedAirplane.getId());

            return airplaneMapper.toResponse(savedAirplane);
        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while creating airplane: " + e.getMessage());
        }
    }

    /**
     * Updates specified fields of an existing airplane identified by its ID.
     * 
     * @param id Airplane ID to be updated
     * @param airplaneDTO airplane details for update
     * @return updated airplane as a response DTO
     * @throws AirplaneNotFoundException if the airplane with the given ID is not found
     * @throws NoChangesMadeException if no changes are applied to the airplane
     */
    @Override
    @Transactional
    public AirplaneResponseDTO partialUpdateAirplane(Long id, PartialAirplaneRequestDTO airplaneDTO) {
        Airplane airplane = airplaneRepository.findById(id)
                .orElseThrow(() -> new AirplaneNotFoundException(id));
    
        boolean isUpdated = updateEntityFields(airplane, airplaneDTO);
        if (!isUpdated) {
            throw new NoChangesMadeException("airplane");
        }

        Airplane updatedAirplane = airplaneRepository.save(airplane);
    
        log.info("Airplane with ID {} successfully updated.", id);
    
        return airplaneMapper.toResponse(updatedAirplane);
    }
    
    /**
     * Updates airplane fields based on the provided DTO.
     * 
     * @param airplane the airplane to update
     * @param airplaneDTO the new data
     * @return true if updated, false otherwise
     */
    private boolean updateEntityFields(Airplane airplane, PartialAirplaneRequestDTO airplaneDTO) {
        boolean isUpdated = false;
    
        for (Field field : PartialAirplaneRequestDTO.class.getDeclaredFields()) {
            try {
                field.setAccessible(true); 
                Object dtoValue = field.get(airplaneDTO);
                if (dtoValue != null) {
                    Field airplaneField = Airplane.class.getDeclaredField(field.getName());
                    airplaneField.setAccessible(true); 
                    Object airplaneValue = airplaneField.get(airplane);
    
                    if (!Objects.equals(airplaneValue, dtoValue)) {
                        airplaneField.set(airplane, dtoValue);
                        isUpdated = true;
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("Error updating field: {} Exception: {}", field.getName(), e.getMessage());
            }
        }
    
        return isUpdated;
    }
    

    /**
     * Deletes spesific airplane by its unique ID.
     * 
     * @param airplaneId ID of the airplane to delete
     * @throws AirplaneNotFoundException if the airplane with the given ID is not found
     */
    @Transactional
    public void deleteAirplane(Long airplaneId) {
        if (!airplaneRepository.existsById(airplaneId)) {
            throw new AirplaneNotFoundException(airplaneId);
        }
        
        log.info("Airplane deleted: {}", airplaneId);

        airplaneRepository.deleteById(airplaneId);
    }   
}