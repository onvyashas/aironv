package org.yashas.AirlineManagement.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="flight")
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="flight_id")
    private Long id;

    @NotBlank
    @Column(name="flight_number", unique = true)
    private String flightNumber;

    @NotBlank
    @Column(name="departure")
    private String departure;

    @NotBlank
    @Column(name="destination")
    private String destination;

    @Column(name="departure_time")
    private LocalDateTime departureTime;

    @Column(name="arrival_time")
    private LocalDateTime arrivalTime;

    // Airplane 1--------M Flight
    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "airplane_id", nullable = false)
    private Airplane airplane;

    // Flight 1---------M Reservation
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();
}