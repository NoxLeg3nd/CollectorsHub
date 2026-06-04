package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AddFavouriteDTO {
    private Long userId;
    private Long listingId;
}
