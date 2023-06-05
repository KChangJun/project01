package com.groupc.weather.dto.response.board;

import javax.validation.constraints.NotBlank;

import com.groupc.weather.dto.ResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetSearchListByHashtagResponseDto extends ResponseDto {
    
    @NotBlank
    private String hashtag;
    private String weather;
    private Integer minTemperature;
    private Integer maxTemperature;
    private int userNumber;

}
