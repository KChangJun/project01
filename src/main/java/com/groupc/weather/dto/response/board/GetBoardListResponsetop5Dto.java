package com.groupc.weather.dto.response.board;

import java.util.List;

import com.groupc.weather.dto.ResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBoardListResponsetop5Dto extends ResponseDto{
    private List<BoardListResultTop5Dto> boardList;

    public GetBoardListResponsetop5Dto(List<BoardListResultTop5Dto>resultSet){
        super("SU", "Success");
        this.boardList = resultSet;
    }
}
