package com.unitbv.collectorshub.model.dto;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
