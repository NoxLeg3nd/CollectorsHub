package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AddFavouriteDTO {
    @NonNull
    private Long userId;
    @NonNull
    private Long listingId;
}
