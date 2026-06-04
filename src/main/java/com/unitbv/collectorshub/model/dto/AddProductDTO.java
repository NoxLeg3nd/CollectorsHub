package com.unitbv.collectorshub.model.dto;

import jakarta.annotation.Nullable;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDTO {
    private String productName;
    private String productDescription;
    private String productImage;
    private String productCollection;
    private String productCategory;
    private Integer manufactureYear;
    private Long userId;
}
