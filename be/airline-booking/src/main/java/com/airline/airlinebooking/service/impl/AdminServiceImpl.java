package com.airline.airlinebooking.service.impl;

import com.airline.airlinebooking.dto.AdminDto;
import com.airline.airlinebooking.dto.ChangePasswordDto;
import com.airline.airlinebooking.dto.LoginDto;
import com.airline.airlinebooking.exception.ResourceNotFoundException;
import com.airline.airlinebooking.model.Admin;
import com.airline.airlinebooking.model.Role;
import com.airline.airlinebooking.repository.AdminRepository;
import com.airline.airlinebooking.service.AdminService;
import com.airline.airlinebooking.utils.JWTUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JWTUtils jwtUtils;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setEmail(adminDto.getEmail());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        admin.setImage(adminDto.getImage());
        admin.setRole(Role.ADMIN);
        adminRepository.save(admin);
    }

    @Override
    public String login(LoginDto loginDto) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(loginDto.getEmail());

        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();

            if (passwordEncoder.matches(loginDto.getPassword(), admin.getPassword())) {
                UserDetails userDetails = org.springframework.security.core.userdetails.User
                        .withUsername(admin.getEmail())
                        .password(admin.getPassword())
                        .authorities(Role.ADMIN.name())
                        .build();
                String token = jwtUtils.generateToken(userDetails);
                return "{\"token\": \"" + token + "\", \"role\": \"" + admin.getRole().name() + "\", \"adminId\": " + admin.getId() + "}";
            } else {
                System.out.println("Invalid password");
            }
        } else {
            System.out.println("Admin not found");
        }
        return null;
    }

    @Override
    public boolean isValidUser(String email, String password) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);

        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            return passwordEncoder.matches(password, admin.getPassword());
        }

        return false;
    }

    @Override
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
    }

    @Override
    public boolean changePassword(Long id, ChangePasswordDto changePasswordDto) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (!optionalAdmin.isPresent()) {
            throw new RuntimeException("Admin not found");
        }
        Admin admin = optionalAdmin.get();
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), admin.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        admin.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        adminRepository.save(admin);
        return true;
    }

    @Transactional
    @Override
    public Admin updateAdmin(Long id, AdminDto AdminDto) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        existingAdmin.setFirstName(AdminDto.getFirstName());
        existingAdmin.setLastName(AdminDto.getLastName());

        if (AdminDto.getEmail() != null && !AdminDto.getEmail().isEmpty()) {
            existingAdmin.setEmail(AdminDto.getEmail());
        }
        if (AdminDto.getPassword() != null && !AdminDto.getPassword().isEmpty()) {
            existingAdmin.setPassword(passwordEncoder.encode(AdminDto.getPassword()));
        }
        existingAdmin.setImage(AdminDto.getImage());
        return adminRepository.save(existingAdmin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

}