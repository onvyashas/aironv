package org.yashas.AirlineManagement.repository;

import java.util.List;
import org.yashas.AirlineManagement.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {
    
    List<Flight> findByAirplaneId(Long airplaneId);

    boolean existsByFlightNumber(String flightNumber);
}