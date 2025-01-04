package com.airline.airlinebooking.config.Customer;


import com.airline.airlinebooking.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomerDetails implements UserDetails {

    private final Customer customer;

    public CustomerDetails(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + customer.getRole().name()));
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getEmail();
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

    public Customer getReceptionist() {
        return customer;
    }
    public Long getId() {
        return customer.getId(); // Thêm getter cho ID
    }
}
