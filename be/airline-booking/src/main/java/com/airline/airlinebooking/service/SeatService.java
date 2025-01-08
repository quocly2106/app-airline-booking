package com.airline.airlinebooking.service;

import com.airline.airlinebooking.dto.SeatDto;
import com.airline.airlinebooking.model.Seat;
import com.airline.airlinebooking.model.SeatStatus;

import java.util.List;

public interface SeatService {
    Seat addSeat (SeatDto seatDto);

    void deleteSeat(Long id);

    Seat updateStatusSeat (Long seatId ,  String status);

    Seat cancelSeatBooking(Long seatId);

    List<Seat> getAllSeats();

    Seat getSeatById(Long id);

    List<Seat> getSeatsByStatus(SeatStatus status);

    List<Seat> getSeatsByType(String type);

    List<Seat> getSeatsByFlightId(Long flightId);

    void deleteSeatsByFlightId(Long flightId);


}
