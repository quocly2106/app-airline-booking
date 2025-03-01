package com.airline.airlinebooking.service.impl;


import com.airline.airlinebooking.dto.ServiceDto;
import com.airline.airlinebooking.exception.ResourceNotFoundException;
import com.airline.airlinebooking.model.Admin;
import com.airline.airlinebooking.model.Service;
import com.airline.airlinebooking.repository.AdminRepository;
import com.airline.airlinebooking.repository.ServiceRepository;
import com.airline.airlinebooking.service.ServiceService;
import com.airline.airlinebooking.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public Service addService(ServiceDto serviceDto , MultipartFile imageFile) {
        Service service = convertToEntity(serviceDto);
        if (imageFile != null && !imageFile.isEmpty()) {
            if (!imageUpload.checkExisted(imageFile)) {
                imageUpload.uploadImage(imageFile);
            }
            service.setImage(imageFile.getOriginalFilename());
        }

        String email = getCurrentUsername();
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with email: " + email));

        service.setAdmin(admin);

        return serviceRepository.save(service);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    public Service updateService(Long id, ServiceDto serviceDto, MultipartFile imageFile) {
        Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with ID: " + id));

        existingService.setName(serviceDto.getName());
        existingService.setDescription(serviceDto.getDescription());
        existingService.setPrice(serviceDto.getPrice());
        existingService.setStatus(serviceDto.getStatus());
        existingService.setUpdatedAt(LocalDateTime.now());

        // Update image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            if (!imageUpload.checkExisted(imageFile)) {
                imageUpload.uploadImage(imageFile);
            }
            existingService.setImage(imageFile.getOriginalFilename());
        }

        return serviceRepository.save(existingService);
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public List<Service> getAllService() {
        return serviceRepository.findAll();
    }

    @Override
    public Service getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with ID: " + id));
    }

    private Service convertToEntity(ServiceDto serviceDto){
        Service service = new Service();
        service.setName(serviceDto.getName());
        service.setDescription(serviceDto.getDescription());
        service.setImage(serviceDto.getImage());
        service.setPrice(serviceDto.getPrice());
        service.setStatus(serviceDto.getStatus());
        LocalDateTime now = LocalDateTime.now();
        service.setCreatedAt(now);
        service.setUpdatedAt(now);
        return service;
    }
}
