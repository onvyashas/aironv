package org.yashas.AirlineManagement.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name="airplane")
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="airplane_id")
    private Long id;

    @NotBlank
    @Column(name="tail_number", unique = true)
    private String tailNumber;

    @NotBlank
    @Column(name="model")
    private String model;

    @Min(1)
    @Column(name="capacity")
    private int capacity;

    @Column(name="production_year")
    private int productionYear;

    @Column(name="status")
    private boolean status;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL)
    private List<Flight> flights = new ArrayList<>();
}