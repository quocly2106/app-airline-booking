package com.airline.airlinebooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // Quan hệ nhiều Booking - 1 Customer

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight; // Quan hệ nhiều Booking - 1 Flight

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service; // Quan hệ nhiều Booking - 1 Service (Có thể null)

    @Column(nullable = false)
    private Date bookingDate; // Ngày đặt

    @Column(nullable = false)
    private int quantity; // Số lượng vé

    @Column(nullable = false)
    private String status; // Trạng thái Booking (VD: Confirmed, Pending)

    @Column(nullable = false)
    private double totalPrice; // Tổng giá vé

    @Column(nullable = false)
    private String createdAt; // Thời gian tạo Booking

    @Column(nullable = false)
    private String updatedAt; // Thời gian cập nhật Booking
}
