package com.airline.airlinebooking.service.impl;

import com.airline.airlinebooking.dto.FeedbackDto;
import com.airline.airlinebooking.exception.ResourceNotFoundException;
import com.airline.airlinebooking.exception.UnauthorizedAccessException;
import com.airline.airlinebooking.model.Customer;
import com.airline.airlinebooking.model.Feedback;
import com.airline.airlinebooking.repository.CustomerRepository;
import com.airline.airlinebooking.repository.FeedbackRepository;
import com.airline.airlinebooking.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Override
    public Feedback addFeedback(Long customerId, FeedbackDto feedbackDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        if (feedbackDto.getRating() < 1.0 || feedbackDto.getRating() > 5.0) {
            throw new IllegalArgumentException("Rating must be between 1.0 and 5.0");
        }
        Feedback feedback = new Feedback();
        feedback.setCustomer(customer);
        feedback.setContent(feedbackDto.getContent());
        feedback.setRating(feedbackDto.getRating());
        LocalDateTime now = LocalDateTime.now();
        feedback.setCreateAt(now);
        feedback.setUpdateAt(now);
        return feedbackRepository.save(feedback);

    }

    @Override
    public Feedback updateFeedback(Long customerId, Long FeedbackId, FeedbackDto feedbackDto) {
        Feedback existingFeedbacks = feedbackRepository.findById(FeedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("FeedbackId not found with ID: " + FeedbackId));

        if (!existingFeedbacks.getCustomer().getId().equals(customerId)) {
            throw new UnauthorizedAccessException("You are not authorized to update this feedback.");
        }

        existingFeedbacks.setContent(feedbackDto.getContent());
        existingFeedbacks.setUpdateAt(LocalDateTime.now());
        return feedbackRepository.save(existingFeedbacks);
    }

    @Override
    public void deleteFeedback(Long customerId, Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));

        if (!feedback.getCustomer().getId().equals(customerId)) {
            throw new UnauthorizedAccessException("You are not authorized to delete this feedback.");
        }
        feedbackRepository.delete(feedback);
    }


    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}
