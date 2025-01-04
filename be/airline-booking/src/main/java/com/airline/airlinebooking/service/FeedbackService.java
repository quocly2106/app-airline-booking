package com.airline.airlinebooking.service;

import com.airline.airlinebooking.dto.FeedbackDto;
import com.airline.airlinebooking.model.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback addFeedback (Long CustomerId , FeedbackDto feedbackDto);
    Feedback updateFeedback(Long CustomerId,Long FeedbackId, FeedbackDto feedbackDto);
    void deleteFeedback(Long CustomerId, Long FeedbackId);

    List<Feedback> getAllFeedbacks();
}
