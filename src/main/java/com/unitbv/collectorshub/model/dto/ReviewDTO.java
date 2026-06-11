package com.unitbv.collectorshub.model.dto;

import jakarta.persistence.Id;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long reviewedUserId;
    private String reviewedUsername;
    private Long reviewingUserId;
    private String reviewingUsername;
    private String comment;
    private Integer opinion;
}
