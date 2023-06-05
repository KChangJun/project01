package com.groupc.weather.dto.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindByPasswordRequestDto {
    @NotBlank
    @Email
    private String userEmail;
    @NotBlank
    private String userPhoneNumber;
}
