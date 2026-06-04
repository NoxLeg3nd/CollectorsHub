package com.unitbv.collectorshub.repositories;

import com.unitbv.collectorshub.model.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long id);
    Optional<Review> findReviewByReviewedUser_IdAndReviewingUser_Id(Long reviewedUserId, Long reviewingUserId);
    List<Review> findAllByReviewedUser_Id(Long reviewedUserId);
    List<Review> findAllByReviewingUser_Id(Long reviewingUserId);
    int countByReviewedUser_Id(Long reviewedUserId);
    int countByReviewedUser_IdAndOpinion(Long reviewedUserId, Integer opinion);
}