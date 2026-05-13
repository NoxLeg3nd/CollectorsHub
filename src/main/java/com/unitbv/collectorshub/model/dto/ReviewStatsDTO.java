package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewStatsDTO {
    private Long userId;
    private int totalReviews;
    private int positiveReviews;
    private int negativeReviews;
    private double positivePercentage; 
}