package com.airline.airlinebooking.service;

import com.airline.airlinebooking.dto.AdminDto;
import com.airline.airlinebooking.dto.ChangePasswordDto;
import com.airline.airlinebooking.dto.LoginDto;
import com.airline.airlinebooking.model.Admin;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AdminService {
    void register(AdminDto adminDto);

    String  login(LoginDto loginDto);

    public boolean isValidUser(String email, String password);

    Admin getAdminById(Long id);

    boolean changePassword(Long id, ChangePasswordDto changePasswordDto);

    List<Admin> getAllAdmins();

    Admin updateAdmin(Long id, AdminDto AdminDto);

}