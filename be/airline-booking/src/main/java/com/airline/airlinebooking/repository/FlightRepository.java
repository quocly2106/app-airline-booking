package com.airline.airlinebooking.repository;

import com.airline.airlinebooking.dto.FlightDto;
import com.airline.airlinebooking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE f.departureAirport = :departureAirport AND f.destinationAirport = :destinationAirport AND f.departureTime = :departureDate")
    List<Flight> searchFlights(@Param("departureAirport") String departureAirport,
                               @Param("destinationAirport") String destinationAirport,
                               @Param("departureDate") String departureDate);

    @Query("SELECT f FROM Flight f WHERE (:minPrice IS NULL OR f.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR f.price <= :maxPrice) " +
            "AND (:status IS NULL OR f.status = :status) " +
            "AND (:departureTimeRange IS NULL OR f.departureTime LIKE CONCAT(:departureTimeRange, '%'))")
    List<Flight> filterFlights(@Param("minPrice") Double minPrice,
                               @Param("maxPrice") Double maxPrice,
                               @Param("status") String status,
                               @Param("departureTimeRange") String departureTimeRange);


}
