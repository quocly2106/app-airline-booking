package com.airline.airlinebooking.service.impl;

import com.airline.airlinebooking.dto.AirportDto;
import com.airline.airlinebooking.model.Airport;
import com.airline.airlinebooking.model.AirportStatus;
import com.airline.airlinebooking.model.Seat;
import com.airline.airlinebooking.model.SeatStatus;
import com.airline.airlinebooking.repository.AirportRepository;
import com.airline.airlinebooking.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public Airport createAirport(AirportDto airportDto) {
        Airport airport = new Airport();
        airport.setName(airportDto.getName());
        airport.setLocation(airportDto.getLocation());
        airport.setStatus(AirportStatus.valueOf(airportDto.getStatus())); // Convert từ String sang enum
        return airportRepository.save(airport);
    }

    @Override
    public Airport updateAirport(Long id, AirportDto airportDto) {
        Optional<Airport> airportOpt = airportRepository.findById(id);
        if (airportOpt.isPresent()) {
            Airport airport = airportOpt.get();
            airport.setName(airportDto.getName());
            airport.setLocation(airportDto.getLocation());
            airport.setStatus(AirportStatus.valueOf(airportDto.getStatus())); // Convert từ String sang enum
            return airportRepository.save(airport);
        } else {
            throw new RuntimeException("Airport not found with id " + id);
        }
    }

    @Override
    public void deleteAirport(Long id) {
        Optional<Airport> airportOpt = airportRepository.findById(id);
        if (airportOpt.isPresent()) {
            airportRepository.deleteById(id);
        } else {
            throw new RuntimeException("Airport not found with id " + id);
        }
    }

    @Override
    public List<Airport> searchByLocation(String location) {
        return airportRepository.findByLocation(location);
    }

    @Override
    public String getAirportStatus(Long id,String status) {
        Optional<Airport> airportOpt = airportRepository.findById(id);
        if (airportOpt.isPresent()) {
            Airport airport = airportOpt.get();
            airport.setStatus(AirportStatus.valueOf(status)); // Cập nhật trạng thái sân bay
            airportRepository.save(airport);
            return "Airport status updated successfully to " + status;
        } else {
            throw new RuntimeException("Airport not found with id " + id);
        }
    }

    @Override
    public List<Airport> getAirportsByStatus(AirportStatus status) {
        return airportRepository.findByStatus(status);
    }
}
