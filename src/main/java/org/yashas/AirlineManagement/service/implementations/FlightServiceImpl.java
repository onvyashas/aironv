package org.yashas.AirlineManagement.service.implementations;

import org.yashas.AirlineManagement.exception.entityrelated.AirplaneNotFoundException;
import org.yashas.AirlineManagement.exception.entityrelated.FlightNotFoundException;
import org.yashas.AirlineManagement.exception.entityrelated.NoFlightsMatchingCriteriaException;
import org.yashas.AirlineManagement.exception.state.AirplaneNotAvailableException;
import org.yashas.AirlineManagement.exception.state.DuplicateFlightNumberException;
import org.yashas.AirlineManagement.exception.state.InvalidFlightTimeException;
import org.yashas.AirlineManagement.exception.state.NoChangesMadeException;
import org.yashas.AirlineManagement.mapper.FlightMapper;
import org.yashas.AirlineManagement.model.Airplane;
import org.yashas.AirlineManagement.model.Flight;
import org.yashas.AirlineManagement.payload.flight.FlightFilterRequestDTO;
import org.yashas.AirlineManagement.payload.flight.FlightRequestDTO;
import org.yashas.AirlineManagement.payload.flight.FlightResponseDTO;
import org.yashas.AirlineManagement.payload.flight.PartialFlightRequestDTO;
import org.yashas.AirlineManagement.repository.AirplaneRepository;
import org.yashas.AirlineManagement.repository.FlightRepository;
import org.yashas.AirlineManagement.service.interfaces.FlightService;
import org.yashas.AirlineManagement.spesification.FlightSpecification;
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
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirplaneRepository airplaneRepository;
    private final FlightMapper flightMapper;

    /**
     * Retrieves a list of all flights as DTOs.
     *
     * @return list of all flight DTOs, or an empty list if none are found
     */
    @Override
    @Transactional(readOnly = true)
    public List<FlightResponseDTO> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(flightMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific flight by its unique ID.
     *
     * @param id ID of the flight to retrieve
     * @return flight response DTO corresponding to the given ID
     * @throws FlightNotFoundException if no flight with the given ID is found
     */
    @Override
    @Transactional(readOnly = true)
    public FlightResponseDTO getFlightById(Long id) {
        return flightRepository.findById(id)
                .map(flightMapper::toResponse)
                .orElseThrow(() -> new FlightNotFoundException(id));
    }

    /**
     * Retrieves a list of flights associated with a specific airplane ID.
     *
     * @param airplaneId ID of the airplane
     * @return list of flights as FlightResponseDTO assigned to the specified airplane
     * @throws AirplaneNotFoundException if no airplane with the given ID is found
     */
    @Override
    @Transactional(readOnly = true)
    public List<FlightResponseDTO> getFlightsByAirplaneId(Long airplaneId) {
        Airplane airplane = airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new AirplaneNotFoundException(airplaneId));

        List<Flight> flights = flightRepository.findByAirplaneId(airplane.getId());
        return flights.stream()
                .map(flightMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Filters flights based on various criteria such as departure location, destination,
     * and departure/arrival dates.
     *
     * @param filter filtering criteria encapsulated in a DTO
     * @return list of flights matching the provided criteria
     * @throws NoFlightsMatchingCriteriaException if no flights match the criteria
     */
    @Override
    @Transactional(readOnly = true)
    public List<FlightResponseDTO> getFilteredFlights(FlightFilterRequestDTO filter) {
        List<Flight> flights = flightRepository.findAll(FlightSpecification.filterFlights(filter));
        if (flights.isEmpty()) {
            throw new NoFlightsMatchingCriteriaException();
        }
        return flights.stream()
                      .map(flightMapper::toResponse)
                      .collect(Collectors.toList());
    }
    
    /**
     * Creates a new flight entry in the repository.
     * Associates an airplane, validates time constraints, and saves the flight.
     *
     * @param flightDTO flight details for creation
     * @return the created flight response DTO
     * @throws AirplaneNotFoundException if the associated airplane is not found
     * @throws AirplaneNotAvailableException if the airplane is unavailable
     * @throws InvalidFlightTimeException if the flight departure time is after the arrival time
     * @throws DuplicateFlightNumberException if the flight number is already assigned
     */
    @Override
    @Transactional
    public FlightResponseDTO createFlight(FlightRequestDTO flightDTO) {
        Airplane airplane = airplaneRepository.findById(flightDTO.getAirplaneId())
                .orElseThrow(() -> new AirplaneNotFoundException(flightDTO.getAirplaneId()));

        if (!airplane.isStatus()) {
            throw new AirplaneNotAvailableException();
        }
        if (flightDTO.getDepartureTime().isAfter(flightDTO.getArrivalTime())) {
            throw new InvalidFlightTimeException();
        }

        if (flightRepository.existsByFlightNumber(flightDTO.getFlightNumber())) {
            throw new DuplicateFlightNumberException(flightDTO.getFlightNumber());
        }
        
        Flight flight = flightMapper.toEntity(flightDTO, airplaneRepository);
        flight.setAirplane(airplane);

        Flight savedFlight = flightRepository.save(flight);

        log.info("New flight created with ID: {}", savedFlight.getId());

        return flightMapper.toResponse(savedFlight);
    }

    /**
     * Updates specified fields of an existing flight identified by its ID
     *
     * @param flightId ID of the flight to update
     * @param flightDTO flight details for update
     * @return updated flight as a response DTO
     * @throws FlightNotFoundException if the flight with the given ID is not found
     * @throws NoChangesMadeException if no changes are applied to the flight
     */
    @Override
    @Transactional
    public FlightResponseDTO partialUpdateFlight(Long flightId, PartialFlightRequestDTO flightDTO) {
        Flight existingFlight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(flightId));
    
        boolean isUpdated = updateEntityFields(existingFlight, flightDTO);
        if (!isUpdated) {
            throw new NoChangesMadeException("flight");
        }
    
        Flight updatedFlight = flightRepository.save(existingFlight);
    
        log.info("Flight with ID {} successfully updated.", flightId);

        return flightMapper.toResponse(updatedFlight);
    }
    
    /**
     * Updates flight fields based on the provided DTO.
     *
     * @param flight the flight to update
     * @param flightDTO the new data
     * @return true if updated, false otherwise
     */
    private boolean updateEntityFields(Flight flight, PartialFlightRequestDTO flightDTO) {
        boolean isUpdated = false;
    
        Long existingAirplaneId = flight.getAirplane() != null ? flight.getAirplane().getId() : null;
        if (flightDTO.getAirplaneId() != null && !Objects.equals(existingAirplaneId, flightDTO.getAirplaneId())) {
        
            Airplane airplane = airplaneRepository.findById(flightDTO.getAirplaneId())
                    .orElseThrow(() -> new AirplaneNotFoundException(flightDTO.getAirplaneId()));
            flight.setAirplane(airplane);
            isUpdated = true;
            log.info("Updated airplane with ID: {}", flightDTO.getAirplaneId());
        }
    
        if (flightDTO.getDepartureTime() != null && flightDTO.getArrivalTime() != null) {
            if (flightDTO.getDepartureTime().isAfter(flightDTO.getArrivalTime())) {
                throw new InvalidFlightTimeException();
            }
        }
    
        for (Field field : PartialFlightRequestDTO.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object dtoValue = field.get(flightDTO);
                if (dtoValue != null) {
                    Field flightField = Flight.class.getDeclaredField(field.getName());
                    flightField.setAccessible(true);
                    Object flightValue = flightField.get(flight);
                    
                    if (!Objects.equals(flightValue, dtoValue)) {
                        flightField.set(flight, dtoValue);
                        isUpdated = true;
                        log.info("Updated field: {} with value: {}", field.getName(), dtoValue);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("Error updating field: {}", field.getName(), e);
            }
        }
    
        return isUpdated;
    }              

    /**
     * Deletes a specific flight by its unique ID.
     *
     * @param flightId ID of the flight to delete
     * @throws FlightNotFoundException if the flight with the given ID is not found
     */
    @Transactional
    public void deleteFlight(Long flightId) {
        if (!flightRepository.existsById(flightId)) {
            throw new FlightNotFoundException(flightId);
        }

        log.info("Flight with ID {} has been deleted", flightId);

        flightRepository.deleteById(flightId);
    }
}