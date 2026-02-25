package com.unitbv.collectorshub.model.dto;

import jakarta.persistence.Id;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Id
    private Long id;
    @NonNull
    private String email;
    @NonNull
    private String username;
    @NonNull
    private String password;
}
