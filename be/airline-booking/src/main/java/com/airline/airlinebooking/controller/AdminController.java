package com.airline.airlinebooking.controller;

import com.airline.airlinebooking.dto.AdminDto;
import com.airline.airlinebooking.dto.ChangePasswordDto;
import com.airline.airlinebooking.model.Admin;
import com.airline.airlinebooking.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AdminDto adminDto) {
        adminService.register(adminDto);
        return ResponseEntity.ok("Admin registered successfully");
    }


//    @GetMapping("/admin/dashboard")
//    public ResponseEntity<String> getDashboard() {
//        return ResponseEntity.ok("Welcome to the admin dashboard!");
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable @NotNull Long id) {
        Admin admin = adminService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable @NotNull Long id,
                                                 @Valid @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            adminService.changePassword(id, changePasswordDto);
            return ResponseEntity.ok("Password changed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        System.out.println("Fetching all admins");
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody AdminDto adminDto) {

        Admin updatedAdmin = adminService.updateAdmin(id, adminDto);
        return ResponseEntity.ok(updatedAdmin);
    }

}