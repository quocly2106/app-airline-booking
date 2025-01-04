package com.airline.airlinebooking.service;

import com.airline.airlinebooking.dto.PlaneDto;
import com.airline.airlinebooking.model.Plane;

import java.util.List;

public interface PlaneService {
    Plane addPlane(PlaneDto planeDto);
    Plane updatePlane(Long id ,PlaneDto planeDto);
    void deletePlane(Long id);

    List<Plane> getAllPlanes();
    Plane getPlaneById(Long id);
}
