package com.groupc.weather.dto.response.board;

import java.util.List;

import com.groupc.weather.entity.HashTagEntity;
import com.groupc.weather.entity.resultSet.GetBoardListResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardListResultDto {
    private int boardNumber;
    private String boardTitle;
    private String boardContent;
    private String boardWriteDatetime;
    private String boardWriterNickname;
    private String boardWriterProfileImageUrl;
    private int likeCount;
    private int commentCount;
    private String boardFirstImageUrl;
    private List<HashTagEntity> hashtagList;

     public BoardListResultDto(GetBoardListResult resultSet, List<HashTagEntity> hashtagList){
        this.boardNumber = resultSet.getBoardNumber();
        this.boardTitle = resultSet.getBoardTitle();
        this.boardContent = resultSet.getBoardContent();
        this.boardWriteDatetime = resultSet.getBoardWriteDatetime();
        this.boardWriterNickname = resultSet.getBoardWriterNickname();
        this.boardWriterProfileImageUrl = resultSet.getBoardWriterProfileImageUrl();
        this.likeCount = resultSet.getLikeCount();
        this.commentCount = resultSet.getCommentCount();
        this.boardFirstImageUrl = resultSet.getBoardFirstImageUrl();
        this.hashtagList=hashtagList;
}
}
