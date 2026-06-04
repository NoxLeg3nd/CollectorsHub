package com.unitbv.collectorshub.model.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddUserDTO {
    private String username;
    private String password;
    private String email;
}
