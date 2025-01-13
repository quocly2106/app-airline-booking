package com.airline.airlinebooking.service;


import com.airline.airlinebooking.dto.AirportDto;
import com.airline.airlinebooking.model.Airport;
import com.airline.airlinebooking.model.AirportStatus;
import com.airline.airlinebooking.model.Seat;
import com.airline.airlinebooking.model.SeatStatus;

import java.util.List;

public interface AirportService {
    Airport createAirport(AirportDto airportDto);
    Airport updateAirport(Long id, AirportDto airportDto);
    void deleteAirport(Long id);
    List<Airport> searchByLocation(String location);

    String getAirportStatus(Long id ,String status);

    List<Airport> getAirportsByStatus(AirportStatus status);
}

