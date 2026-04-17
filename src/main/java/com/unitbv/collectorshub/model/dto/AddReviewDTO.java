package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewDTO {
    @NonNull
    private Long reviewedUserId;
    @NonNull
    private Long reviewingUserId;
    @NonNull
    private String comment;
    @NonNull
    private Integer opinion;
}