package com.unitbv.collectorshub.model.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private String collection;
    private Integer manufactureYear;
    private String image;
    private String description;
    private Long userId;
}