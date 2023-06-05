package com.groupc.weather.dto.response.board;

import java.util.List;

import com.groupc.weather.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetPostListDto extends ResponseDto {
    private List<BoardSummary> boardList;
}

@Data
@NoArgsConstructor
class BoardSummary {
    private int boardNumber;
    private String boardTitle;
    private String boardContetn;
    private String boardfisrtImageUrl;
    private String boardWriteDatetime;
    private String boardWriterNickname;
    private String boardWriterProfileImageUrl;
    private String hashtagList;
    private int commentCount;
    private int likeCount;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class HashList {
    private int boardNumber;
    private int hashTagNumber;
    private String hashTagContent;
}