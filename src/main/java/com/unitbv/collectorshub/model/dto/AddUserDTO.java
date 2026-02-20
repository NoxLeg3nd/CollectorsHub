package com.unitbv.collectorshub.model.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddUserDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
}
