package org.yashas.AirlineManagement.spesification;

import org.yashas.AirlineManagement.model.Flight;
import org.yashas.AirlineManagement.payload.flight.FlightFilterRequestDTO;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class FlightSpecification {
    public static Specification<Flight> filterFlights(FlightFilterRequestDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Departure location filter
            if (filter.getDepartureLocation() != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("departure"), 
                    filter.getDepartureLocation()));
            }

            // Arrival location filter
            if (filter.getArrivalLocation() != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("destination"), 
                    filter.getArrivalLocation()));
            }

            // Departure date filter
            if (filter.getDepartureDate() != null) {
                predicates.add(criteriaBuilder.between(
                    root.get("departureTime"),
                    filter.getDepartureDate().atStartOfDay(),
                    filter.getDepartureDate().atTime(23, 59, 59)
                ));
            }

            // Arrival date filter
            if (filter.getArrivalDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("arrivalTime"), 
                    filter.getArrivalDate().atTime(23, 59, 59)
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}