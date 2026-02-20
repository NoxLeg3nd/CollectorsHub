package com.unitbv.collectorshub.model.dto;

import jakarta.annotation.Nullable;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDTO {
    @NonNull
    private String productName;
    @NonNull
    private String productDescription;
    @Nullable
    private String productImage;
    @NonNull
    private String productCollection;
    @NonNull
    private String productCategory;
    @NonNull
    private Integer manufactureYear;
    @NonNull
    private Long userId;
}
