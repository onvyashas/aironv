package org.yashas.AirlineManagement.mapper;

import org.yashas.AirlineManagement.model.Reservation;
import org.yashas.AirlineManagement.payload.reservation.ReservationRequestDTO;
import org.yashas.AirlineManagement.payload.reservation.ReservationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.yashas.AirlineManagement.repository.FlightRepository;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "reservationCode", ignore = true)
    @Mapping(target = "flight", ignore = true)
    Reservation toEntity(ReservationRequestDTO reservationRequest, FlightRepository flightRepository);

    @Mapping(source = "flight.id", target = "flightId")
    ReservationResponseDTO toResponse(Reservation reservation);
}
