package com.airline.airlinebooking.service.impl;

import com.airline.airlinebooking.dto.FlightDto;
import com.airline.airlinebooking.model.Airport;
import com.airline.airlinebooking.model.Flight;
import com.airline.airlinebooking.model.FlightStatus;
import com.airline.airlinebooking.model.Plane;
import com.airline.airlinebooking.repository.AirportRepository;
import com.airline.airlinebooking.repository.FlightRepository;
import com.airline.airlinebooking.repository.PlaneRepository;
import com.airline.airlinebooking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public FlightDto createFlight(FlightDto flightDto) {
        Flight flight = new Flight();

        Plane plane = planeRepository.findById(flightDto.getPlaneId())
                .orElseThrow(() -> new RuntimeException("Plane not found with ID: " + flightDto.getPlaneId()));
        flight.setPlane(plane);

        Airport departureAirport = airportRepository.findById(flightDto.getDepartureAirportId())
                .orElseThrow(() -> new RuntimeException("Departure Airport not found with ID: " + flightDto.getDepartureAirportId()));
        flight.setDepartureAirport(departureAirport);

        Airport destinationAirport = airportRepository.findById(flightDto.getDestinationAirportId())
                .orElseThrow(() -> new RuntimeException("Destination Airport not found with ID: " + flightDto.getDestinationAirportId()));
        flight.setDestinationAirport(destinationAirport);

        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());
        flight.setPrice(flightDto.getPrice());
        String status = flightDto.getStatus();
        if (status != null) {
            flight.setStatus(FlightStatus.valueOf(status));
        }
        flight.setCreatedAt(flightDto.getCreatedAt());
        flight.setUpdatedAt(flightDto.getUpdatedAt());

        Flight savedFlight = flightRepository.save(flight);
        return mapToDto(savedFlight);
    }

    @Override
    public FlightDto updateFlight(Long flightId, FlightDto flightDto) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + flightId));

        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());
        flight.setPrice(flightDto.getPrice());
        String status = flightDto.getStatus();
        if (status != null) {
            flight.setStatus(FlightStatus.valueOf(status));
        }
        flight.setUpdatedAt(flightDto.getUpdatedAt());

        Plane plane = planeRepository.findById(flightDto.getPlaneId())
                .orElseThrow(() -> new RuntimeException("Plane not found with ID: " + flightDto.getPlaneId()));
        flight.setPlane(plane);

        Airport departureAirport = airportRepository.findById(flightDto.getDepartureAirportId())
                .orElseThrow(() -> new RuntimeException("Departure Airport not found with ID: " + flightDto.getDepartureAirportId()));
        flight.setDepartureAirport(departureAirport);

        Airport destinationAirport = airportRepository.findById(flightDto.getDestinationAirportId())
                .orElseThrow(() -> new RuntimeException("Destination Airport not found with ID: " + flightDto.getDestinationAirportId()));
        flight.setDestinationAirport(destinationAirport);

        Flight updatedFlight = flightRepository.save(flight);
        return mapToDto(updatedFlight);
    }

    @Override
    public void deleteFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }

    @Override
    public List<FlightDto> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightDto> searchFlights(String departureAirport, String destinationAirport, String departureDate) {
        List<Flight> flights = flightRepository.searchFlights(departureAirport, destinationAirport, departureDate);
        return flights.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<FlightDto> filterFlights(Double minPrice, Double maxPrice, String status, String departureTimeRange) {
        return flightRepository.filterFlights(minPrice, maxPrice, status, departureTimeRange)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void updateFlightStatus(Long flightId, String status) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + flightId));
        try {
            flight.setStatus(FlightStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
        flightRepository.save(flight);
    }

    @Override
    public void notifyCustomers(Long flightId, String message) {

    }

    // Helper method to map Flight to FlightDto
    private FlightDto mapToDto(Flight flight) {
        FlightDto dto = new FlightDto();
        dto.setPlaneId(flight.getPlane().getId());
        dto.setDepartureAirportId(flight.getDepartureAirport().getId());
        dto.setDestinationAirportId(flight.getDestinationAirport().getId());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setPrice(flight.getPrice());
        dto.setStatus(flight.getStatus() != null ? flight.getStatus().toString() : null);
        dto.setCreatedAt(flight.getCreatedAt());
        dto.setUpdatedAt(flight.getUpdatedAt());
        return dto;
    }
}
