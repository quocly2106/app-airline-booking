package com.airline.airlinebooking.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Flight flight;

    @Column(nullable = false)
    private String type; // Loại ghế (Economy, Business, First Class)

    @Column(nullable = false)
    private String seatNumber; // Ví dụ: 12A, 14B

    @Column(nullable = false)
    private String status; // Tình trạng (Available, Reserved, etc.)
}
