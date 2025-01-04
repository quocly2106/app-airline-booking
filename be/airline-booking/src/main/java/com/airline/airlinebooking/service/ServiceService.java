package com.airline.airlinebooking.service;


import com.airline.airlinebooking.dto.ServiceDto;
import com.airline.airlinebooking.model.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServiceService {
    Service addService(ServiceDto serviceDto, MultipartFile imageFile);
    Service updateService(Long id, ServiceDto serviceDto, MultipartFile imageFile);
    void deleteService(Long id);
    List<Service> getAllService();
    Service getServiceById(Long id);
}
