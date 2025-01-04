package com.airline.airlinebooking.service;

import com.airline.airlinebooking.dto.AdminDto;
import com.airline.airlinebooking.dto.ChangePasswordDto;
import com.airline.airlinebooking.dto.LoginDto;
import com.airline.airlinebooking.dto.StaffDto;
import com.airline.airlinebooking.model.Admin;
import com.airline.airlinebooking.model.Staff;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StaffService {
    void register(StaffDto staffDto);

    String  login(LoginDto loginDto);

    public boolean isValidUser(String email, String password);

    Staff getStaffById(Long id);

    boolean changePassword(Long id, ChangePasswordDto changePasswordDto);

    List<Staff> getAllStaffs();

    Staff addStaff(StaffDto staffDto , MultipartFile imageFile);

    Staff updateStaff(Long id, StaffDto StaffDto);

    void  deleteStaff(Long id);
}
