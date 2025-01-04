package com.airline.airlinebooking.service.impl;

import com.airline.airlinebooking.config.Staff.StaffDetails;
import com.airline.airlinebooking.dto.AdminDto;
import com.airline.airlinebooking.dto.ChangePasswordDto;
import com.airline.airlinebooking.dto.LoginDto;
import com.airline.airlinebooking.dto.StaffDto;
import com.airline.airlinebooking.exception.ResourceNotFoundException;
import com.airline.airlinebooking.model.Admin;
import com.airline.airlinebooking.model.Role;
import com.airline.airlinebooking.model.Staff;
import com.airline.airlinebooking.repository.StaffRepository;
import com.airline.airlinebooking.service.StaffService;
import com.airline.airlinebooking.utils.ImageUpload;
import com.airline.airlinebooking.utils.JWTUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private ImageUpload imageUpload;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void register(StaffDto StaffDto) {
        Staff staff = new Staff();
        staff.setFirstName(StaffDto.getFirstName());
        staff.setLastName(StaffDto.getLastName());
        staff.setGender(StaffDto.getGender());
        staff.setDateOfBirth(StaffDto.getDateOfBirth());
        staff.setPhone(StaffDto.getPhone());
        staff.setEmail(StaffDto.getEmail());
        staff.setPassword(passwordEncoder.encode(StaffDto.getPassword()));
        staff.setImage(StaffDto.getImage());
        staff.setRole(Role.STAFF);
        staffRepository.save(staff);
    }

    @Override
    public String login(LoginDto loginDto) {
        Optional<Staff> optionalStaff = staffRepository.findByEmail(loginDto.getEmail());

        if (optionalStaff.isPresent()) {
            Staff staff = optionalStaff.get();

            if (passwordEncoder.matches(loginDto.getPassword(), staff.getPassword())) {
                UserDetails staffDetails = new StaffDetails(staff);
                String token = jwtUtils.generateToken(staffDetails);

                // Trả về một JSON String với token, role và receptionistId
                return "{\"token\": \"" + token + "\", \"role\": \"" + staff.getRole().name() + "\", \"staffId\": " + staff.getId() + "}";
            }
        }
        return null;
    }

    @Override
    public boolean isValidUser(String email, String password) {
        Optional<Staff> optionalStaff = staffRepository.findByEmail(email);

        if (optionalStaff.isPresent()) {
            Staff staff = optionalStaff.get();
            return passwordEncoder.matches(password, staff.getPassword());
        }

        return false;
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
    }

    @Override
    public boolean changePassword(Long id, ChangePasswordDto changePasswordDto) {
        Optional<Staff> optionalStaff = staffRepository.findById(id);
        if (!optionalStaff.isPresent()) {
            throw new RuntimeException("Staff not found");
        }
        Staff staff = optionalStaff.get();
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), staff.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        staff.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        staffRepository.save(staff);
        return true;
    }


    @Override
    public List<Staff> getAllStaffs() {
        return staffRepository.findAll();
    }

    @Override
    public Staff addStaff(StaffDto staffDto, MultipartFile imageFile) {
        Staff staff = convertToEntity(staffDto);
        if (imageFile != null && !imageFile.isEmpty()) {
            if (!imageUpload.checkExisted(imageFile)) {
                imageUpload.uploadImage(imageFile);
            }
            staff.setImage(imageFile.getOriginalFilename());
        }
        return staffRepository.save(staff);
    }


    @Transactional
    @Override
    public Staff updateStaff(Long id, StaffDto StaffDto) {
        Staff existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found"));

        existingStaff.setFirstName(StaffDto.getFirstName());
        existingStaff.setLastName(StaffDto.getLastName());
        existingStaff.setGender(StaffDto.getGender());
        existingStaff.setDateOfBirth(StaffDto.getDateOfBirth());
        existingStaff.setPhone(StaffDto.getPhone());

        if (StaffDto.getEmail() != null && !StaffDto.getEmail().isEmpty()) {
            existingStaff.setEmail(StaffDto.getEmail());
        }
        if (StaffDto.getPassword() != null && !StaffDto.getPassword().isEmpty()) {
            existingStaff.setPassword(passwordEncoder.encode(StaffDto.getPassword()));
        }
        existingStaff.setImage(StaffDto.getImage());
        return staffRepository.save(existingStaff);
    }

    @Override
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }


    private Staff convertToEntity(StaffDto staffDto) {
        Staff staff = new Staff();
        staff.setFirstName(staffDto.getFirstName());
        staff.setLastName(staffDto.getLastName());
        staff.setGender(staffDto.getGender());
        staff.setDateOfBirth(staffDto.getDateOfBirth());
        staff.setPhone(staffDto.getPhone());
        staff.setEmail(staffDto.getEmail());
        staff.setPassword(passwordEncoder.encode(staffDto.getPassword()));
        staff.setImage(staffDto.getImage());
        staff.setRole(Role.STAFF);
        return staff;
    }
}
