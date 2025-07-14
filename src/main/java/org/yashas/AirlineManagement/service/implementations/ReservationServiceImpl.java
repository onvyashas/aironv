package org.yashas.AirlineManagement.service.implementations;

import org.apache.commons.lang3.RandomStringUtils;
import org.yashas.AirlineManagement.exception.entityrelated.FlightNotFoundException;
import org.yashas.AirlineManagement.exception.entityrelated.ReservationNotFoundException;
import org.yashas.AirlineManagement.exception.state.FlightFullyBookedException;
import org.yashas.AirlineManagement.exception.state.NoChangesMadeException;
import org.yashas.AirlineManagement.mapper.ReservationMapper;
import org.yashas.AirlineManagement.model.Flight;
import org.yashas.AirlineManagement.model.Reservation;
import org.yashas.AirlineManagement.payload.reservation.PartialReservationRequestDTO;
import org.yashas.AirlineManagement.payload.reservation.ReservationRequestDTO;
import org.yashas.AirlineManagement.payload.reservation.ReservationResponseDTO;
import org.yashas.AirlineManagement.repository.FlightRepository;
import org.yashas.AirlineManagement.repository.ReservationRepository;
import org.yashas.AirlineManagement.service.interfaces.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationEmailService emailService;

    /**
     * Retrieves a list of all reservations as DTOs.   
     * 
     * @return list of reservation response DTOs, or an empty list if none are found
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a spesific reservation by its unique ID.
     *
     * @param id ID of the reservation to retrieve
     * @return reservation response DTO corresponding to the given ID
     * @throws ReservationNotFoundException if no reservation with the given ID is found
     */
    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toResponse)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    /**
     * Retrieves a list of reservations associated with a specific flight ID.
     *
     * @param flightId the ID of the flight
     * @return list of reservations as ReservationResponseDTO assigned to the specified flight
     * @throws FlightNotFoundException if no flight with the given ID is found
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByFlightId(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(flightId));

        List<Reservation> reservations = reservationRepository.findByFlightId(flight.getId());
        return reservations.stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new reservation entry in the repository.
     *
     * @param reservationDTO reservation details for creation
     * @return the created reservation response DTO
     * @throws FlightNotFoundException if the associated flight is not found
     * @throws FlightFullyBookedException if the flight is already fully booked
     */
    @Override
    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationDTO) {
        Flight flight = flightRepository.findById(reservationDTO.getFlightId())
                .orElseThrow(() -> new FlightNotFoundException(reservationDTO.getFlightId()));
        
        checkFlightCapacity(flight);

        String reservationCode = createReservationCode();
    
        Reservation reservation = createReservationEntity(reservationDTO, flight, reservationCode);
        Reservation savedReservation = reservationRepository.save(reservation);
        ReservationResponseDTO responseDTO = reservationMapper.toResponse(savedReservation);
    
        emailService.sendConfirmationEmail(responseDTO);

        log.info("Created new reservation with code: {}", reservationCode);
 
        return responseDTO;
    }

    /**
     * Generates a unique alphanumeric reservation code.
     *
     * @return a unique 6-character reservation code
     */
    private String createReservationCode() {
        String reservationCode;
        do {
            reservationCode = RandomStringUtils.randomAlphanumeric(6);
        } while (reservationRepository.existsByReservationCode(reservationCode));
        return reservationCode;
    }

    /**
     * Creates a reservation entity from a request DTO.
     *
     * @param reservationDTO the reservation request data
     * @param flight the associated flight
     * @param reservationCode the generated reservation code
     * @return a populated reservation entity
     */
    private Reservation createReservationEntity(ReservationRequestDTO reservationDTO, Flight flight, String reservationCode) {
        Reservation reservation = reservationMapper.toEntity(reservationDTO, flightRepository);
        reservation.setFlight(flight);
        reservation.setReservationCode(reservationCode);
        reservation.setStatus(true);
        reservation.setCreatedAt(LocalDateTime.now());
        return reservation;
    }

    /**
     * Partially updates an existing reservation.
     *
     * @param id the ID of the reservation to update
     * @param reservationDTO reservation details for update
     * @return updated reservation as a response DTO
     * @throws ReservationNotFoundException if the reservation with the given ID is not found
     * @throws NoChangesMadeException if no changes are applied to the reservation
     */
    @Override
    @Transactional
    public ReservationResponseDTO partialUpdateReservation(Long id, PartialReservationRequestDTO reservationDTO) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    
        String oldEmail = existingReservation.getPassengerEmail();
        Flight newFlight = validateAndGetNewFlight(reservationDTO.getFlightId()).orElse(null);
    
        boolean isUpdated = updateEntityFields(existingReservation, reservationDTO, newFlight);
        if (!isUpdated) throw new NoChangesMadeException("reservation");
    
        Reservation updatedReservation = reservationRepository.save(existingReservation);
        ReservationResponseDTO responseDTO = reservationMapper.toResponse(updatedReservation);
    
        handleNotifications(responseDTO, oldEmail, newFlight != null, newFlight);
    
        log.info("Reservation with ID {} successfully updated.", id);
        return responseDTO;
    }
        
    /**
     * Sends email notifications after updating a reservation.
     *
     * @param reservationDTO updated reservation details
     * @param oldEmail previous email
     * @param isFlightChanged if the flight was changed
     * @param newFlight the new flight (if changed)
     */
    private void handleNotifications(ReservationResponseDTO reservationDTO, String oldEmail, boolean isFlightChanged, Flight newFlight) {
        String newEmail = reservationDTO.getPassengerEmail();
        boolean isEmailChanged = !Objects.equals(oldEmail, newEmail);
    
        if (isEmailChanged) {
            emailService.sendConfirmationEmail(reservationDTO);
        }
        if (isFlightChanged) {
            emailService.sendFlightChangeNotificationEmail(reservationDTO, newFlight.getId());
        }
    }

    /**
     * Validates and retrieves the new flight by ID.
     *
     * @param flightId new flight ID
     * @return validated flight or empty if invalid
     * @throws FlightNotFoundException if the flight with the given ID is not found
     * @throws FlightFullyBookedException if the flight is fully booked
     */
    private Optional<Flight> validateAndGetNewFlight(Long flightId) {
        if (flightId == null) return Optional.empty();
    
        Flight newFlight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(flightId));
    
        checkFlightCapacity(newFlight);
        return Optional.of(newFlight);
    }

    /**
     * Checks if the flight is fully booked.
     * 
     * @param flight the flight to check
     * @throws FlightFullyBookedException if the flight is fully booked
     */
    private void checkFlightCapacity(Flight flight) {
        if (reservationRepository.countByFlightIdAndStatusTrue(flight.getId()) >= flight.getAirplane().getCapacity()) {
            throw new FlightFullyBookedException();
        }
    }

    /**
     * Updates reservation fields based on the provided DTO.
     *
     * @param reservation the reservation to update
     * @param reservationDTO the new data
     * @param newFlight additional validation is required for the unmatched property in ReservationDTO
     * @return true if updated, false otherwise
     */
    private boolean updateEntityFields(Reservation reservation, PartialReservationRequestDTO reservationDTO, Flight newFlight) {
        boolean isUpdated = false;

        if (newFlight != null && !Objects.equals(newFlight.getId(), reservation.getFlight().getId())) {
            reservation.setFlight(newFlight);
            isUpdated = true;
        }
    
        for (Field field : PartialReservationRequestDTO.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object dtoValue = field.get(reservationDTO);
                if (dtoValue != null) {
                    Field reservationField = Reservation.class.getDeclaredField(field.getName());
                    reservationField.setAccessible(true);
                    Object reservationValue = reservationField.get(reservation);
    
                    if (!Objects.equals(dtoValue, reservationValue)) {
                        reservationField.set(reservation, dtoValue);
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
}