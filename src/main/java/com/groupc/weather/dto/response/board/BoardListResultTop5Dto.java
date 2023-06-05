package com.groupc.weather.dto.response.board;

import com.groupc.weather.entity.resultSet.GetBoardListResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardListResultTop5Dto {
    private Integer boardNumber;
    private String boardTitle;
    private String boardFirstImageUrl;

    public BoardListResultTop5Dto(GetBoardListResult result){
        this.boardNumber = result.getBoardNumber();
        this.boardTitle = result.getBoardTitle();
        this.boardFirstImageUrl = result.getBoardFirstImageUrl();
    }
}
