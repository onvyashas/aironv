package org.yashas.AirlineManagement.mapper;

import org.yashas.AirlineManagement.model.Flight;
import org.yashas.AirlineManagement.payload.flight.FlightRequestDTO;
import org.yashas.AirlineManagement.payload.flight.FlightResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.yashas.AirlineManagement.repository.AirplaneRepository;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "airplane", ignore = true)
    Flight toEntity(FlightRequestDTO flightRequest, AirplaneRepository airplaneRepository);

    @Mapping(source = "airplane.id", target = "airplaneId")
    FlightResponseDTO toResponse(Flight flight);
}
