package com.groupc.weather.dto.request.manager;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostManagerRequestDto {
   
    @NotNull
    private String nickname;
    @NotNull
    private String password;
    @NotNull
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String phoneNumber;
    @NotNull
    @Email
    private String email;
    private String profileImageUrl;
}
