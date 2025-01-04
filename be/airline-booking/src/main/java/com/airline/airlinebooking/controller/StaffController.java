package com.airline.airlinebooking.controller;

import com.airline.airlinebooking.dto.ChangePasswordDto;
import com.airline.airlinebooking.dto.StaffDto;
import com.airline.airlinebooking.model.Staff;
import com.airline.airlinebooking.service.StaffService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/staffs")
public class StaffController {
    @Autowired
    private StaffService staffService;


    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable @NotNull Long id) {
        Staff staff = staffService.getStaffById(id);
        return ResponseEntity.ok(staff);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('STAFF') and #id == authentication.principal.id)")
    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable @NotNull Long id,
                                                 @Valid @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            staffService.changePassword(id, changePasswordDto);
            return ResponseEntity.ok("Password changed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Staff>> getAllStaffs() {
        System.out.println("Fetching all staffs");
        List<Staff> staffs = staffService.getAllStaffs();
        return ResponseEntity.ok(staffs);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Staff> createDoctor(@Valid @RequestPart StaffDto staffDto,
                                               @RequestPart(value = "image", required = false) MultipartFile image) {
        Staff createdStaff = staffService.addStaff(staffDto ,image);
        return ResponseEntity.ok(createdStaff);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('STAFF') and #id == authentication.principal.id)")
    @PutMapping("/update/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody StaffDto staffDto) {

        Staff updatedStaff = staffService.updateStaff(id, staffDto);
        return ResponseEntity.ok(updatedStaff);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable @NotNull Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff deleted successfully");
    }

}