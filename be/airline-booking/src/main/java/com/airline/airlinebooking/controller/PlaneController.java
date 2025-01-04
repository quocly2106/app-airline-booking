package com.airline.airlinebooking.controller;

import com.airline.airlinebooking.dto.PlaneDto;
import com.airline.airlinebooking.model.News;
import com.airline.airlinebooking.model.Plane;
import com.airline.airlinebooking.service.PlaneService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/planes")
public class PlaneController {
    @Autowired
    private PlaneService planeService;

    @PostMapping("/add")
    public ResponseEntity<Plane> addPlane( @Valid @RequestBody PlaneDto planeDto){
        Plane createdPlane = planeService.addPlane(planeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlane);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Plane> updatePlane(@PathVariable @NotNull Long id , @Valid @RequestBody PlaneDto planeDto){
        Plane updatedPlane = planeService.updatePlane(id , planeDto);
        return ResponseEntity.ok(updatedPlane);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletedPlane(@PathVariable @NotNull Long id){
       planeService.deletePlane(id);
       return ResponseEntity.ok("Plane deleted successfully");

    }

    @GetMapping("/all")
    public ResponseEntity<List<Plane>> getAllPlanes() {
        List<Plane> planesList = planeService.getAllPlanes();
        return ResponseEntity.ok(planesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plane> getPlanesById(@PathVariable @NotNull Long id) {
        Plane plane = planeService.getPlaneById(id);
        return ResponseEntity.ok(plane);
    }
}
