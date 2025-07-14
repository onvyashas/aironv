package org.yashas.AirlineManagement.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="reservation")
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="reservation_id")
    private Long id;

    @NotBlank
    @Column(name="passenger_name")
    private String passengerName;

    @Email
    @Column(name="passenger_email")
    private String passengerEmail;

    @Column(name="reservation_code", unique = true)
    private String reservationCode;
    
    @Column(name="status")
    private boolean status;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id", nullable = false)
    private Flight flight;
}