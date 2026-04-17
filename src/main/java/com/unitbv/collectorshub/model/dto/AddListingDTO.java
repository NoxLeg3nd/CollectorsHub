package com.unitbv.collectorshub.model.dto;

import com.unitbv.collectorshub.model.entities.User;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddListingDTO {

    @NonNull
    private Long productId;
    @NonNull
    private String link;
    @NonNull
    private String contact;
    @NonNull
    private Boolean isActive;
    @NonNull
    private BigDecimal price;
    @NonNull
    private String description;
    @NonNull
    private Long userId;

}
