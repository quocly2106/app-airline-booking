package com.airline.airlinebooking.service.impl;

import com.airline.airlinebooking.dto.SeatDto;
import com.airline.airlinebooking.model.Flight;
import com.airline.airlinebooking.model.Seat;
import com.airline.airlinebooking.model.SeatStatus;
import com.airline.airlinebooking.repository.FlightRepository;
import com.airline.airlinebooking.repository.SeatRepository;
import com.airline.airlinebooking.service.SeatService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Override
    @Transactional
    public Seat addSeat(SeatDto seatDto) {
        Seat seat = new Seat();
        Flight flight = flightRepository.findById(seatDto.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + seatDto.getFlightId()));
        seat.setFlight(flight);
        seat.setType(seatDto.getType());
        seat.setSeatNumber(seatDto.getSeatNumber());
        seat.setStatus(SeatStatus.fromString(seatDto.getStatus()));
        return seatRepository.save(seat);
    }

    @Override
    public void deleteSeat(Long id) {
        seatRepository.deleteById(id);
    }

    @Override
    public Seat updateStatusSeat(Long id, String status) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + id));
        seat.setStatus(SeatStatus.valueOf(status));
        return seatRepository.save(seat);
    }

    @Override
    public Seat cancelSeatBooking(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));
        seat.setStatus(SeatStatus.CANCELLED);
        return seatRepository.save(seat);
    }

    @Override
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    @Override
    public Seat getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + id));
    }

    @Override
    public List<Seat> getSeatsByStatus(SeatStatus  status) {
        return seatRepository.findByStatus(status);
    }

    @Override
    public List<Seat> getSeatsByType(String type) {
        return seatRepository.findByType(type);
    }

    @Override
    public List<Seat> getSeatsByFlightId(Long flightId) {
        return seatRepository.findByFlightId(flightId);
    }

    @Override
    @Transactional
    public void deleteSeatsByFlightId(Long flightId) {
        seatRepository.deleteByFlightId(flightId);
    }
}
