package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditReviewDTO {
    private String newComment;
    private Integer newOpinion;
}
