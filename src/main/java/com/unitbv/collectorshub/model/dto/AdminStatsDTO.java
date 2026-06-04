package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatsDTO {
    private long totalUsers;
    private long totalProducts;
    private long totalListings;
    private long activeListings;
    private long totalReviews;
    private long totalFavourites;
}