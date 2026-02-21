package com.unitbv.collectorshub.model.dto;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

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

}
