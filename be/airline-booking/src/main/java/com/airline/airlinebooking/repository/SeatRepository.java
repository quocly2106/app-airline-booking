package com.airline.airlinebooking.repository;

import com.airline.airlinebooking.model.Seat;
import com.airline.airlinebooking.model.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat , Long> {
    List<Seat> findByStatus(SeatStatus status);
    List<Seat> findByType(String type);
    List<Seat> findByFlightId(Long flightId);
    void deleteByFlightId(Long flightId);
}
