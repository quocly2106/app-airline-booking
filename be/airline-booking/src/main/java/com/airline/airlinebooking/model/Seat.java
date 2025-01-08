package com.airline.airlinebooking.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonBackReference
    private Flight flight;

    @Column(nullable = false)
    private String type; // Loại ghế (Economy, Business, First Class)

    @Column(nullable = false, unique = true)
    private String seatNumber; // Ví dụ: 12A, 14B

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
}
