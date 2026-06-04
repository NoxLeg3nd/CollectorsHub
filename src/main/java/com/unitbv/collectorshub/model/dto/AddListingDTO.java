package com.unitbv.collectorshub.model.dto;

import com.unitbv.collectorshub.model.entities.User;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddListingDTO {

    private Long productId;
    private String link;
    private String contact;
    private Boolean isActive;
    private BigDecimal price;
    private String description;
    private Long userId;
}
