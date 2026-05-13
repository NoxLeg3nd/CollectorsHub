package com.unitbv.collectorshub.model.dto;

import jakarta.persistence.Id;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    @Id
    private Long id;
    @NonNull
    private Long reviewedUserId;
    @NonNull
    private Long reviewingUserId;
    private String reviewingUsername;
    @NonNull
    private String comment;
    @NonNull
    private Integer opinion;
}
