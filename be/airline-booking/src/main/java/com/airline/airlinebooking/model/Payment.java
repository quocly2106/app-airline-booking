package com.airline.airlinebooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking; // Quan hệ 1-1 với Booking

    @Column(nullable = false)
    private String paymentMethod; // Phương thức thanh toán (VD: Credit Card, PayPal)

    @Column(nullable = false)
    private String status; // Trạng thái thanh toán (Paid, Pending, etc.)

    @Column(nullable = false)
    private String transactionDate; // Ngày giao dịch

    @Column(nullable = false)
    private double amount; // Số tiền thanh toán
}
