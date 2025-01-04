package com.airline.airlinebooking.config.Customer;

import com.airline.airlinebooking.model.Customer;
import com.airline.airlinebooking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        Customer customer = optionalCustomer.orElseThrow(() ->
                new UsernameNotFoundException("Reception with email " + email + " not found")
        );
        System.out.println("Loading user: " + customer.getEmail() + " with role: " + customer.getRole());
        return new CustomerDetails(customer);
    }
}
