package com.groupc.weather.dto.response.board;

import java.util.List;

import com.groupc.weather.dto.ResponseDto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBoardFirstViewDto extends ResponseDto {
    private List<BoardFirstViewDto> boardList;  

    public GetBoardFirstViewDto(List<BoardFirstViewDto> resultSet){
        super("SU","Success");
        this.boardList = resultSet;
    }
}