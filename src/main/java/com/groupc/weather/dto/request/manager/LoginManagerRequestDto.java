package com.groupc.weather.dto.request.manager;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginManagerRequestDto {
    @NotBlank
    @Email
    private String managerEmail;
    @NotBlank
    private String managerPassword;
}
