package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewDTO {
    private Long reviewedUserId;
    private Long reviewingUserId;
    private String comment;
    private Integer opinion;
}