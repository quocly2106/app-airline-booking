package com.airline.airlinebooking.service;

import com.airline.airlinebooking.dto.FlightDto;

import java.util.List;

public interface FlightService {

    // Quản lý
    FlightDto createFlight(FlightDto flightDto);

    FlightDto updateFlight(Long flightId, FlightDto flightDto);

    void deleteFlight(Long flightId);

    List<FlightDto> getAllFlights();

    // Tìm kiếm và lọc chuyến bay
    List<FlightDto> searchFlights(String departureAirport, String destinationAirport, String departureDate);

    List<FlightDto> filterFlights(Double minPrice, Double maxPrice, String status, String departureTimeRange);


    // Thông báo và trạng thái chuyến bay
    void updateFlightStatus(Long flightId, String status);

    void notifyCustomers(Long flightId, String message);


}
