package com.airline.airlinebooking.controller;

import com.airline.airlinebooking.dto.FeedbackDto;
import com.airline.airlinebooking.model.Feedback;
import com.airline.airlinebooking.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PreAuthorize("hasRole('CUSTOMER') and #customerId == authentication.principal.id")
    @PostMapping("/{customerId}/add")
    public ResponseEntity<Feedback> addFeedback(
            @PathVariable Long customerId,
            @RequestBody FeedbackDto feedbackDto) {
        Feedback feedback = feedbackService.addFeedback(customerId, feedbackDto);
        return new ResponseEntity<>(feedback, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('CUSTOMER') and #customerId == authentication.principal.id")
    @DeleteMapping("/{customerId}/{feedbackId}/delete")
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long customerId,
            @PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(customerId, feedbackId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CUSTOMER') and #customerId == authentication.principal.id")
    @PutMapping("/{customerId}/{feedbackId}/update")
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable Long customerId,
            @PathVariable Long feedbackId,
            @RequestBody FeedbackDto feedbackDto) {
        Feedback updatedFeedback = feedbackService.updateFeedback(customerId, feedbackId, feedbackDto);
        return ResponseEntity.ok(updatedFeedback);
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedbacks(){
        List<Feedback> feedback = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedback);
    }
}