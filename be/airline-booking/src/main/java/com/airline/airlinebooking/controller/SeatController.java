package com.airline.airlinebooking.controller;

import com.airline.airlinebooking.dto.SeatDto;
import com.airline.airlinebooking.model.Seat;
import com.airline.airlinebooking.model.SeatStatus;
import com.airline.airlinebooking.service.SeatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Seat> addSeat(@Valid @RequestBody SeatDto seatDto){
        Seat addSeat = seatService.addSeat(seatDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addSeat);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable @NotNull Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/all")
    public ResponseEntity<List<Seat>> getAllSeats() {
        List<Seat> seatList = seatService.getAllSeats();
        return ResponseEntity.ok(seatList);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable @NotNull Long id) {
        Seat seat = seatService.getSeatById(id);
        return ResponseEntity.ok(seat);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Seat>> getSeatsByStatus(@PathVariable String status) {
        SeatStatus seatStatus = SeatStatus.fromString(status); // Dùng phương thức từ enum để chuyển đổi
        List<Seat> seatByStatus = seatService.getSeatsByStatus(seatStatus);
        return ResponseEntity.ok(seatByStatus);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Seat>> getSeatsByType(@PathVariable String type) {
        List<Seat> seatByType = seatService.getSeatsByType(type);
        return ResponseEntity.ok(seatByType);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<Seat>> getSeatsByFlightId(@PathVariable @NotNull Long flightId) {
        List<Seat> seatByFlightId = seatService.getSeatsByFlightId(flightId);
        return ResponseEntity.ok(seatByFlightId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/update/{seatId}/status/{status}")
    public ResponseEntity<Seat> updateStatusSeat(@PathVariable @NotNull Long seatId, @PathVariable @NotNull String status) {
        Seat updateStatusSeat = seatService.updateStatusSeat(seatId, status);
        return ResponseEntity.ok(updateStatusSeat);
    }



    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or (hasRole('CUSTOMER') and @seatSecurity.isOwner(authentication, #seatId))")
    @PutMapping("/cancel/{seatId}")
    public ResponseEntity<Seat> cancelSeatBooking(@PathVariable @NotNull Long seatId) {
        Seat canceledSeat = seatService.cancelSeatBooking(seatId);
        return ResponseEntity.ok(canceledSeat);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/flight/{flightId}")
    public ResponseEntity<Void> deleteSeatsByFlightId(@PathVariable @NotNull Long flightId) {
        seatService.deleteSeatsByFlightId(flightId);
        return ResponseEntity.noContent().build();
    }

}
