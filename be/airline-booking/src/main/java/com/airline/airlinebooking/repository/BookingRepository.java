package com.airline.airlinebooking.repository;

import com.airline.airlinebooking.model.Booking;
import com.airline.airlinebooking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByFlightAndCustomerId(Flight flight, Long customerId);
}
