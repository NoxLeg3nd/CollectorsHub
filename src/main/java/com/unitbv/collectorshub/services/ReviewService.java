package com.unitbv.collectorshub.services;


import com.unitbv.collectorshub.exceptions.ApiException;
import com.unitbv.collectorshub.model.dto.AddReviewDTO;
import com.unitbv.collectorshub.model.dto.EditReviewDTO;
import com.unitbv.collectorshub.model.dto.ReviewDTO;
import com.unitbv.collectorshub.model.entities.Review;
import com.unitbv.collectorshub.model.entities.User;
import com.unitbv.collectorshub.repositories.ReviewRepository;
import com.unitbv.collectorshub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public AddReviewDTO addReview(AddReviewDTO addReviewDTO) {
        User reviewedUser = userRepository.findById(addReviewDTO.getReviewedUserId())
                .orElseThrow(() -> new RuntimeException("Reviewed user not found"));
        User reviewingUser = userRepository.findById(addReviewDTO.getReviewingUserId())
                .orElseThrow(() -> new RuntimeException("Reviewing user not found"));
        boolean exists = reviewRepository
                .findReviewByReviewedUser_IdAndReviewingUser_Id(
                        addReviewDTO.getReviewedUserId(),
                        addReviewDTO.getReviewingUserId()
                )
                .isPresent();

        if (exists) {
            throw new ApiException("You already reviewed this user. Please edit your review instead.", 409);
        }
        Review review = Review.builder()
                .comment(addReviewDTO.getComment())
                .opinion(addReviewDTO.getOpinion())
                .reviewedUser(reviewedUser)
                .reviewingUser(reviewingUser)
                .build();

        if(addReviewDTO.getOpinion() >= 2 || addReviewDTO.getOpinion() < 0) {
            throw new ApiException("Invalid opinion value", 400);
        }
        reviewRepository.save(review);
        return addReviewDTO;
    }

    public EditReviewDTO editReview(Long reviewedUserId, Long reviewingUserId, EditReviewDTO editReviewDTO) {
        Review review = reviewRepository.findReviewByReviewedUser_IdAndReviewingUser_Id(reviewedUserId, reviewingUserId)
                .orElseThrow(() -> new ApiException("Review not found", 404));

        if (editReviewDTO.getNewComment().isBlank()) {
            throw new ApiException("Fields cannot be empty", 400);
        }
        if (editReviewDTO.getNewOpinion() >= 2 || editReviewDTO.getNewOpinion() < 0) {
            throw new ApiException("Invalid opinion value", 400);
        }

        review.setOpinion(editReviewDTO.getNewOpinion());
        review.setComment(editReviewDTO.getNewComment());

        reviewRepository.save(review);
        return editReviewDTO;
    }

    public void removeReview(Long reviewedUserId, Long reviewingUserId) {
        Review review = reviewRepository.findReviewByReviewedUser_IdAndReviewingUser_Id(reviewedUserId, reviewingUserId)
                .orElseThrow(() -> new ApiException("Review not found", 404));
        reviewRepository.delete(review);
    }
}
