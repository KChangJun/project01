package com.groupc.weather.dto.response.board;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.entity.BoardEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWeatherDataResponseDto extends ResponseDto {
    
    private String weatherMain;
    private String weatherDescription;
    private int temperature;
    
    public GetWeatherDataResponseDto(BoardEntity boardEntity) {
        super("SU", "Success");

        this.weatherMain = boardEntity.getWeatherMain();
        this.weatherDescription = boardEntity.getWeatherDescription();
        this.temperature = boardEntity.getTemperature();
    }

}
