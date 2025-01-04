package com.airline.airlinebooking.service.impl;

import com.airline.airlinebooking.config.Customer.CustomerDetails;
import com.airline.airlinebooking.dto.ChangePasswordDto;
import com.airline.airlinebooking.dto.CustomerDto;
import com.airline.airlinebooking.dto.LoginDto;
import com.airline.airlinebooking.exception.ResourceNotFoundException;
import com.airline.airlinebooking.model.Customer;
import com.airline.airlinebooking.model.Role;
import com.airline.airlinebooking.model.Staff;
import com.airline.airlinebooking.repository.CustomerRepository;
import com.airline.airlinebooking.service.CustomerService;
import com.airline.airlinebooking.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JWTUtils jwtUtils;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public void register(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setGender(customerDto.getGender());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setPhone(customerDto.getPhone());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer.setImage(customerDto.getImage());
        customer.setRole(Role.CUSTOMER);
        customerRepository.save(customer);
    }

    @Override
    public String login(LoginDto loginDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(loginDto.getEmail());

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            if (passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())) {
                UserDetails customerDetails = new CustomerDetails(customer);
                String token = jwtUtils.generateToken(customerDetails);

                // Trả về một JSON String với token, role và receptionistId
                return "{\"token\": \"" + token + "\", \"role\": \"" + customer.getRole().name() + "\", \"customerId\": " + customer.getId() + "}";
            }
        }
        return null;
    }

    @Override
    public boolean isValidUser(String email, String password) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return passwordEncoder.matches(password, customer.getPassword());
        }

        return false;
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
    @Override
    public boolean changePassword(Long id, ChangePasswordDto changePasswordDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()) {
            throw new RuntimeException("Customer not found");
        }
        Customer customer = optionalCustomer.get();
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), customer.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        customer.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        customerRepository.save(customer);
        return true;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDto CustomerDto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        existingCustomer.setFirstName(CustomerDto.getFirstName());
        existingCustomer.setLastName(CustomerDto.getLastName());
        existingCustomer.setGender(CustomerDto.getGender());
        existingCustomer.setDateOfBirth(CustomerDto.getDateOfBirth());
        existingCustomer.setPhone(CustomerDto.getPhone());

        if (CustomerDto.getEmail() != null && !CustomerDto.getEmail().isEmpty()) {
            existingCustomer.setEmail(CustomerDto.getEmail());
        }
        if (CustomerDto.getPassword() != null && !CustomerDto.getPassword().isEmpty()) {
            existingCustomer.setPassword(passwordEncoder.encode(CustomerDto.getPassword()));
        }
        existingCustomer.setImage(CustomerDto.getImage());
        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

}
