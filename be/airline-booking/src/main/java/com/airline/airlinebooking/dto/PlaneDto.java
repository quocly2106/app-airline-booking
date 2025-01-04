package com.airline.airlinebooking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaneDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Nation is required")
    private String nation;
    @NotBlank(message = "Airline is required")
    private String airline;
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be greater than 0")
    private Integer capacity;

    @NotNull(message = "Manufacture is required")
    @Min(value = 1900, message = "Manufacture year must be later than 1900")
    private Integer manufacture;
    @NotBlank(message = "Status is required")
    private String status;
}
