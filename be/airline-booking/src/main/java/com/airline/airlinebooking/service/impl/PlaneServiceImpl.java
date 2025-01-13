package com.airline.airlinebooking.service.impl;

import com.airline.airlinebooking.dto.PlaneDto;
import com.airline.airlinebooking.exception.ResourceNotFoundException;
import com.airline.airlinebooking.model.Admin;
import com.airline.airlinebooking.model.Plane;
import com.airline.airlinebooking.model.PlaneStatus;
import com.airline.airlinebooking.repository.AdminRepository;
import com.airline.airlinebooking.repository.PlaneRepository;
import com.airline.airlinebooking.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaneServiceImpl implements PlaneService {

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Override
    public Plane addPlane(PlaneDto planeDto) {
        Plane plane = convertToEntity(planeDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ token
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with email: " + email));

        // Gán author cho news
        plane.setAdmin(admin);
        return planeRepository.save(plane);
    }

    @Override
    public Plane updatePlane(Long id, PlaneDto planeDto) {
        // Tìm plane hiện tại theo ID
        Plane existingPlane = planeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plane not found with ID: " + id));

        // Cập nhật các thông tin từ planeDto
        existingPlane.setName(planeDto.getName());
        existingPlane.setNation(planeDto.getNation());
        existingPlane.setAirline(planeDto.getAirline());
        existingPlane.setCapacity(planeDto.getCapacity());
        existingPlane.setManufacture(planeDto.getManufacture());
        existingPlane.setStatus(PlaneStatus.valueOf(planeDto.getStatus()));

        // Lưu lại plane đã cập nhật
        return planeRepository.save(existingPlane);
    }

    @Override
    public void deletePlane(Long id) {
        if (!planeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plane not found with ID: " + id);
        }
        planeRepository.deleteById(id);
    }

    @Override
    public List<Plane> getAllPlanes() {
        return planeRepository.findAll();
    }

    @Override
    public Plane getPlaneById(Long id) {
        return planeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planes not found with ID: " + id));
    }

    private Plane convertToEntity(PlaneDto planeDto) {
        Plane plane = new Plane();
        plane.setName(planeDto.getName());
        plane.setNation(planeDto.getStatus());
        plane.setAirline(planeDto.getAirline());
        plane.setCapacity(planeDto.getCapacity());
        plane.setManufacture(planeDto.getManufacture());
        plane.setStatus(PlaneStatus.valueOf(planeDto.getStatus()));
        return plane;
    }

}
