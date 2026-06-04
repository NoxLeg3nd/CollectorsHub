package com.unitbv.collectorshub.model.dto;

import jakarta.persistence.Id;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDTO {
    private Long id;
    private String email;
    private String username;
    private String role;
}