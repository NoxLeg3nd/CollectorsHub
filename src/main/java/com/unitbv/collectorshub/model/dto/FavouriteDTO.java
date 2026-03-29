package com.unitbv.collectorshub.model.dto;

import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteDTO {
    @Id
    private Long id;
    @NonNull
    private Long userId;
    @NonNull
    private ListingDTO listing;
}
