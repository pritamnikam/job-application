package com.spring.firstjobapp.review.impl;

import com.spring.firstjobapp.company.Company;
import com.spring.firstjobapp.company.CompanyService;
import com.spring.firstjobapp.review.Review;
import com.spring.firstjobapp.review.ReviewRepository;
import com.spring.firstjobapp.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return this.reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean createReview(Long companyId, Review review) {
        Company company = this.companyService.getCompany(companyId);
        if (company != null) {
            review.setCompany(company);
            this.reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewById(Long companyId, Long id) {
        List<Review> reviews = this.reviewRepository.findByCompanyId(companyId);
        return reviews.stream().filter(review -> review.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long id, Review updatedReview) {
        Company company = this.companyService.getCompany(companyId);
        if (company != null) {
            updatedReview.setCompany(company);
            updatedReview.setId(id);
            this.reviewRepository.save(updatedReview);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReviewById(Long companyId, Long id) {

        if (this.companyService.getCompany(companyId) != null && this.reviewRepository.existsById(id)) {
            Review review = this.reviewRepository.getById(id);
            Company company = review.getCompany();
            company.getReviews().remove(review);
            review.setCompany(null);
            this.companyService.updateCompany(companyId, company);
            this.reviewRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
