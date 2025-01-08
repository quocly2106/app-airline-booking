package com.airline.airlinebooking.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatDto {

    private Long  flightId;

    @NotBlank(message = "Type is required ")
    private String type; // Loại ghế (Economy, Business, First Class)

    @NotBlank(message = "Seat Number is required ")
    private String seatNumber; // Ví dụ: 12A, 14B

    @NotBlank(message = "Status is required ")
    private String status; // Tình trạng (Available, Reserved, etc.)

}
