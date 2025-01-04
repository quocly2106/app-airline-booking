package com.airline.airlinebooking.service;

import com.airline.airlinebooking.dto.ChangePasswordDto;
import com.airline.airlinebooking.dto.CustomerDto;
import com.airline.airlinebooking.dto.LoginDto;
import com.airline.airlinebooking.dto.StaffDto;
import com.airline.airlinebooking.model.Customer;
import com.airline.airlinebooking.model.Staff;

import java.util.List;

public interface CustomerService {
    void register(CustomerDto customerDto);

    String  login(LoginDto loginDto);

    public boolean isValidUser(String email, String password);

    Customer getCustomerById(Long id);

    boolean changePassword(Long id, ChangePasswordDto changePasswordDto);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Long id, CustomerDto CustomerDto);

    void deleteCustomer(Long id);
}
