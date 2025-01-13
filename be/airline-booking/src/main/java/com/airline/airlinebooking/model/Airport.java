package com.airline.airlinebooking.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "airports")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AirportStatus status;

    @OneToMany(mappedBy = "departureAirport", cascade = CascadeType.ALL)
    private Set<Flight> departureFlights;

    @OneToMany(mappedBy = "destinationAirport", cascade = CascadeType.ALL)
    private Set<Flight> destinationFlights;
}
