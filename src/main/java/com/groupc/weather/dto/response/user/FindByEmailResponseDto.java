package com.groupc.weather.dto.response.user;

import com.groupc.weather.dto.ResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindByEmailResponseDto extends ResponseDto {
    private String userEmail;

    public FindByEmailResponseDto(String userEmail) {
        super("SU", "Success");
        this.userEmail = userEmail;
    }
}
