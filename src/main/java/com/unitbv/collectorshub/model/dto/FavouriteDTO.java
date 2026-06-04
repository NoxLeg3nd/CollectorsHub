package com.unitbv.collectorshub.model.dto;

import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteDTO {
    private Long id;
    private Long userId;
    private ListingDTO listing;
}
