package com.spring.firstjobapp.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    boolean createReview(Long companyId, Review review);
    Review getReviewById(Long companyId, Long id);
    boolean updateReview(Long companyId, Long id, Review updatedReview);
    boolean deleteReviewById(Long companyId, Long id);
}
