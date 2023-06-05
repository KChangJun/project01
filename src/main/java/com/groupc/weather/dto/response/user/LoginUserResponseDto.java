package com.groupc.weather.dto.response.user;

import com.groupc.weather.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserResponseDto extends ResponseDto {
    private String token;
    private int expirationDate;

    public LoginUserResponseDto(String token) {
        super("SU", "Success");
        this.token = token;
        this.expirationDate = 3600;
    }
}
