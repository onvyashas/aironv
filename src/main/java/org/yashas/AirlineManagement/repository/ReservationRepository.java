package org.yashas.AirlineManagement.repository;

import java.util.List;
import org.yashas.AirlineManagement.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    List<Reservation> findByFlightId(Long id);
    
    boolean existsByReservationCode(String reservationCode);
    
    int countByFlightIdAndStatusTrue(Long flightId);
}