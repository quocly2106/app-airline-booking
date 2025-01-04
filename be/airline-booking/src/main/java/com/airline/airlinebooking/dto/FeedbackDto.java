package com.airline.airlinebooking.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDto {
    @NotBlank(message = "content is required ")
    private String content;
    @NotBlank(message = "rating is required ")
    private int rating;

}
