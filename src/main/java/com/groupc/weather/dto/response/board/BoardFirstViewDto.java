package com.groupc.weather.dto.response.board;

import com.groupc.weather.entity.resultSet.GetBoardListResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardFirstViewDto{
    private int boardNumber;
    private String boardFirstImageUrl;

    public BoardFirstViewDto(GetBoardListResult resultSet){
        this.boardNumber = resultSet.getBoardNumber();
        this.boardFirstImageUrl = resultSet.getBoardFirstImageUrl();
    }
}
