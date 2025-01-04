package com.airline.airlinebooking.config.Staff;

import com.airline.airlinebooking.model.Staff;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class StaffDetails implements UserDetails {

    private final Staff staff;

    public StaffDetails(Staff staff) {
        this.staff = staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + staff.getRole().name()));
    }

    @Override
    public String getPassword() {
        return staff.getPassword();
    }

    @Override
    public String getUsername() {
        return staff.getEmail(); // Sử dụng email làm username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Bạn có thể thêm logic nếu cần
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Bạn có thể thêm logic nếu cần
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Bạn có thể thêm logic nếu cần
    }

    @Override
    public boolean isEnabled() {
        return true; // Bạn có thể thêm logic nếu cần
    }

    public Staff getStaff() {
        return staff;
    }
    public Long getId() {
        return staff.getId(); // Thêm getter cho ID
    }
}
