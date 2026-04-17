package com.unitbv.collectorshub.controllers;

import com.unitbv.collectorshub.model.dto.AddReviewDTO;
import com.unitbv.collectorshub.model.dto.EditReviewDTO;
import com.unitbv.collectorshub.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping ("/addReview")
    public ResponseEntity<AddReviewDTO> addReview(@RequestBody AddReviewDTO addReviewDTO) {
        return ResponseEntity.ok(reviewService.addReview(addReviewDTO));
    }

    @DeleteMapping ("/removeReview")
    public ResponseEntity<Void> removeReview(@RequestParam Long reviewedUserId, @RequestParam Long reviewingUserId) {
        reviewService.removeReview(reviewedUserId, reviewingUserId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("/editReview")
    public ResponseEntity<EditReviewDTO> editReview(@RequestParam Long reviewedUserId, @RequestParam Long reviewingUserId, @RequestBody EditReviewDTO editReviewDTO) {
        return ResponseEntity.ok(reviewService.editReview(reviewedUserId, reviewingUserId, editReviewDTO));
    }
}
