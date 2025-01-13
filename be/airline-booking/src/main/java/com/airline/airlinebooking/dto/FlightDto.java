package com.airline.airlinebooking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightDto {

    @NotNull(message = "Plane ID is required")
    private Long planeId;

    @NotNull(message = "Departure airport ID is required")
    private Long departureAirportId;

    @NotNull(message = "Destination airport ID is required")
    private Long destinationAirportId;

    @NotBlank(message = "Departure time is required")
    private String departureTime;

    @NotBlank(message = "Arrival time is required")
    private String arrivalTime;

    private Set<Long> seatId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(Scheduled|Cancelled|Delayed|Completed)$", message = "Status must be one of: Scheduled, Cancelled, Delayed, Completed")
    private String status;

    @NotBlank(message = "Created at time is required")
    private String createdAt;

    @NotBlank(message = "Updated at time is required")
    private String updatedAt;
}