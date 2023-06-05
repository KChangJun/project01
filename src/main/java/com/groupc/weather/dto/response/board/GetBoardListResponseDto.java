package com.groupc.weather.dto.response.board;
import java.util.List;
import com.groupc.weather.dto.ResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    public class GetBoardListResponseDto extends ResponseDto{
        //top5 사용!! 데이터 베이스 활용...
        private List<BoardListResultDto> boardList; 
    
    
        public GetBoardListResponseDto(List<BoardListResultDto> resultSet){
            super("SU","Success");
            this.boardList = resultSet;
    }
}

