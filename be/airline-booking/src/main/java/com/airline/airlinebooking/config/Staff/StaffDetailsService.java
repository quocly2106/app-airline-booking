package com.airline.airlinebooking.config.Staff;

import com.airline.airlinebooking.model.Staff;
import com.airline.airlinebooking.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StaffDetailsService implements UserDetailsService {
    private final StaffRepository staffRepository;

    @Autowired
    public StaffDetailsService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Staff> optionalStaff = staffRepository.findByEmail(email);
        Staff staff = optionalStaff.orElseThrow(() ->
                new UsernameNotFoundException("Staff with email " + email + " not found")
        );
        System.out.println("Loading user: " + staff.getEmail() + " with role: " + staff.getRole());
        return new StaffDetails(staff);
    }
}
