package com.airline.airlinebooking.repository;


import com.airline.airlinebooking.model.Airport;
import com.airline.airlinebooking.model.AirportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport , Long> {
    List<Airport> findByLocation(String location);

    List<Airport> findByStatus(AirportStatus status);
}
