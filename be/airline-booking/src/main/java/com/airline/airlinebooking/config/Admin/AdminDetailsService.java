package com.airline.airlinebooking.config.Admin;

import com.airline.airlinebooking.model.Admin;
import com.airline.airlinebooking.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDetailsService implements UserDetailsService {


    private final AdminRepository adminRepository;

    @Autowired
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);

        // Kiểm tra xem Admin có tồn tại không
        Admin admin = optionalAdmin.orElseThrow(() ->
                new UsernameNotFoundException("Admin with email " + email + " not found")
        );

        // Trả về UserDetails
        return org.springframework.security.core.userdetails.User
                .withUsername(admin.getEmail())
                .password(admin.getPassword())
                .roles(admin.getRole().name())
                .build();
    }
}
