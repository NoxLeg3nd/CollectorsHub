package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDTO {
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String email;
}
