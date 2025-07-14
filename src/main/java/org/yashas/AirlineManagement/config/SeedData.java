package org.yashas.AirlineManagement.config;

import java.time.LocalDateTime;
import java.util.List;
import org.yashas.AirlineManagement.model.Airplane;
import org.yashas.AirlineManagement.model.Flight;
import org.yashas.AirlineManagement.model.Reservation;
import org.yashas.AirlineManagement.repository.AirplaneRepository;
import org.yashas.AirlineManagement.repository.FlightRepository;
import org.yashas.AirlineManagement.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

// I added this class to facilitate more efficient and easier testing.
@Component
@RequiredArgsConstructor
public class SeedData implements CommandLineRunner {

    private final AirplaneRepository airplaneRepository;
    private final FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        if (airplaneRepository.count() == 0) {
            Airplane airplane1 = Airplane.builder()
                    .tailNumber("NC-833M")
                    .model("Boeing 40A")
                    .capacity(2)
                    .productionYear(1927)
                    .status(true)
                    .build();

            Airplane airplane2 = Airplane.builder()
                    .tailNumber("TC-JTP")
                    .model("Airbus A321")
                    .capacity(220)
                    .productionYear(1994)
                    .status(true)
                    .build();

            Airplane airplane3 = Airplane.builder()
                    .tailNumber("TC-GMD")
                    .model("Airbus A321neo")
                    .capacity(220)
                    .productionYear(1987)
                    .status(true)
                    .build();

            Airplane airplane4 = Airplane.builder()
                    .tailNumber("TC-BLH")
                    .model("Airbus A310")
                    .capacity(220)
                    .productionYear(2000)
                    .status(true)
                    .build();

            Airplane airplane5 = Airplane.builder()
                    .tailNumber("N787EX")
                    .model("Boeing 787 Dreamliner")
                    .capacity(296)
                    .productionYear(2009)
                    .status(false)
                    .build();

            airplaneRepository.saveAll(List.of(airplane1, airplane2, airplane3, airplane4, airplane5));

            Flight flight1 = Flight.builder()
                    .flightNumber("TK100")
                    .departure("Istanbul Airport")
                    .destination("Francisco Sá Carneiro Airport")
                    .departureTime(LocalDateTime.of(2025, 5, 10, 9, 15))
                    .arrivalTime(LocalDateTime.of(2025, 5, 10, 12, 45))
                    .airplane(airplane2)
                    .build();

            Flight flight2 = Flight.builder()
                    .flightNumber("TK101")
                    .departure("Charles de Gaulle Airport")
                    .destination("Istanbul Airport")
                    .departureTime(LocalDateTime.of(2025, 5, 10, 9, 15))
                    .arrivalTime(LocalDateTime.of(2025, 5, 10, 12, 45))
                    .airplane(airplane3)
                    .build();

            Flight flight3 = Flight.builder()
                    .flightNumber("TK102")
                    .departure("Schiphol Airport")
                    .destination("Istanbul Airport")
                    .departureTime(LocalDateTime.of(2025, 6, 15, 13, 45))
                    .arrivalTime(LocalDateTime.of(2025, 6, 15, 15, 10))
                    .airplane(airplane3)
                    .build();

            Flight flight4 = Flight.builder()
                    .flightNumber("TK103")
                    .departure("Izmir Adnan Menderes Airport")
                    .destination("Berlin Brandenburg Airport")
                    .departureTime(LocalDateTime.of(2025, 7, 20, 6, 30))
                    .arrivalTime(LocalDateTime.of(2025, 7, 20, 10, 45))
                    .airplane(airplane1)
                    .build();

            flightRepository.saveAll(List.of(flight1, flight2, flight3, flight4));

            Reservation reservation1 = Reservation.builder()
                    .passengerName("Günsu Günaydin")
                    .passengerEmail("gunsugunay98@gmail.com")
                    .reservationCode("ASA56")
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .flight(flight1)
                    .build();

            Reservation reservation2 = Reservation.builder()
                    .passengerName("Gökce Günaydin")
                    .passengerEmail("gokcegunaydin@mail.com")
                    .reservationCode("BDSKU")
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .flight(flight2)
                    .build();

            Reservation reservation3 = Reservation.builder()
                    .passengerName("Ahmet Mehmet")
                    .passengerEmail("ahmet.mehmet@mail.com")
                    .reservationCode("TRJ45")
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .flight(flight1)
                    .build();

            Reservation reservation4 = Reservation.builder()
                    .passengerName("Berra Çiçek")
                    .passengerEmail("berracicek@mail.com")
                    .reservationCode("LMN98")
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .flight(flight4)
                    .build();

            Reservation reservation5 = Reservation.builder()
                    .passengerName("Sinem Uğurlu")
                    .passengerEmail("usinem@mail.com")
                    .reservationCode("QWX12")
                    .status(true)
                    .createdAt(LocalDateTime.now())
                    .flight(flight4)
                    .build();

            reservationRepository.saveAll(List.of(reservation1, reservation2, reservation3, reservation4, reservation5));

            System.out.println("Seed data successfully added to the database.");
        } else {
            System.out.println("Data already exists in the database.");
        }
    }
}