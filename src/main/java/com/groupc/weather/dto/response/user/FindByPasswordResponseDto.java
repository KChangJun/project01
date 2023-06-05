package com.groupc.weather.dto.response.user;

import com.groupc.weather.dto.ResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindByPasswordResponseDto extends ResponseDto {
    private String userPassword;

    public FindByPasswordResponseDto(String userPassword) {
        super("SU", "Success");
        this.userPassword = userPassword;
    }
}
