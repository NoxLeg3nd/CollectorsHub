package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditReviewDTO {
    @NonNull
    private String newComment;
    @NonNull
    private Integer newOpinion;
}
