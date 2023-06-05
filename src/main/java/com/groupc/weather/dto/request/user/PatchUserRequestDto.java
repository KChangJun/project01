package com.groupc.weather.dto.request.user;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchUserRequestDto {
    @NotBlank
    @Email
    private String userEmail;
    @NotNull
    private Integer userNumber;
    @NotBlank
    private String userPassword;
    @NotBlank
    private String userNickname;
    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String userPhoneNumber;
    @NotBlank
    private String userAddress;
    @NotBlank
    private String userProfileImageUrl;
    @NotBlank
    private String userGender;
    @NotBlank
    private String userBirthDay;
}
