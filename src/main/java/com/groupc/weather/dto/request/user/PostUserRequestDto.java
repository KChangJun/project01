package com.groupc.weather.dto.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

// 데이터 교환을 하기위한 객체 ( Entity <-> dto )

@Data
@NoArgsConstructor
public class PostUserRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String userNickname;
    @NotBlank
    private String userPassword;
    @NotBlank
    @Email
    private String userEmail;
    private String userProfileImageUrl;
    @NotBlank
    private String userBirthday;
    @NotBlank
    private String userGender;
    @NotBlank
    private String userAddress;
    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String userPhoneNumber;
}
