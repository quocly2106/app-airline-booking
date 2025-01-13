package com.airline.airlinebooking.controller;

import com.airline.airlinebooking.dto.AirportDto;
import com.airline.airlinebooking.model.Airport;
import com.airline.airlinebooking.model.AirportStatus;
import com.airline.airlinebooking.model.Seat;
import com.airline.airlinebooking.model.SeatStatus;
import com.airline.airlinebooking.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/airports")
public class AirportController {
    @Autowired
    private AirportService airportService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Airport> createAirport(@RequestBody AirportDto airportDto) {
        Airport createdAirport = airportService.createAirport(airportDto);
        return new ResponseEntity<>(createdAirport, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody AirportDto airportDto) {
        try {
            Airport updatedAirport = airportService.updateAirport(id, airportDto);
            return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        try {
            airportService.deleteAirport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/search")
    public ResponseEntity<List<Airport>> searchByLocation(@RequestParam String location) {
        List<Airport> airports = airportService.searchByLocation(location);
        if (airports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(airports, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/update/{id}/status")
    public ResponseEntity<String> getAirportStatus(@PathVariable Long id ,@RequestParam String status) {
        try {
            String updatedStatus = airportService.getAirportStatus(id,status);
            return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/status")
    public ResponseEntity<List<Airport>> getAirportsByStatus(@RequestParam String status) {
        try {
            AirportStatus airportStatus = AirportStatus.valueOf(status.toUpperCase()); // Chuyển đổi String sang Enum
            List<Airport> airportsByStatus = airportService.getAirportsByStatus(airportStatus);
            return ResponseEntity.ok(airportsByStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


