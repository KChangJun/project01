package com.groupc.weather.dto.response.board;

import java.util.List;

import com.groupc.weather.dto.ResponseDto;

import com.groupc.weather.entity.CommentEntity;
import com.groupc.weather.entity.HashTagEntity;
import com.groupc.weather.entity.ImageUrlEntity;
import com.groupc.weather.entity.BoardEntity;
import com.groupc.weather.entity.CommentEntity;
import com.groupc.weather.entity.HashTagEntity;
import com.groupc.weather.entity.ImageUrlEntity;
import com.groupc.weather.entity.LikeyEntity;
import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.entity.primaryKey.HashPk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardResponseDto extends ResponseDto {
    private int boardNumber;
    private String boardTitle;
    private String boardContent;
    private String boardWriteDatetime;
    private String weatherDescription;
    private int temperature;
    private int viewCount;
    private String boardWriterNickname;
    private String boardWriterProfileImageUrl;
    private int commentCount;
    private int likeCount;
    private List<CommentEntity> commentList;
    private List<ImageUrlEntity> boardImageUrlList;
    private List<HashTagEntity> hashtagList;
    private List<LikeyListDto> likeListDto;

    // request 요청 하면 => dto => DB = > dto => response
    public GetBoardResponseDto(
            BoardEntity boardEntity, UserEntity userEntity,
            List<LikeyListDto> likeyListDto, List<CommentEntity> commentEntities, List<HashTagEntity> hashListEntities,
            List<ImageUrlEntity> imageUrlEntities) {
        super("SU", "Success");
        this.boardNumber = boardEntity.getBoardNumber();
        this.boardTitle = boardEntity.getTitle();
        this.boardContent = boardEntity.getContent();
        this.boardWriteDatetime = boardEntity.getWriteDatetime();
        this.weatherDescription = boardEntity.getWeatherDescription();
        this.temperature = boardEntity.getTemperature();
        this.viewCount = boardEntity.getViewCount();
        this.boardWriterNickname = userEntity.getNickname();
        this.boardWriterProfileImageUrl = userEntity.getProfileImageUrl();
        this.commentCount = commentEntities.size();
        this.likeCount = likeyListDto.size();
        this.hashtagList = hashListEntities;
        this.boardImageUrlList= imageUrlEntities;
        this.commentList = commentEntities;
        this.likeListDto = likeyListDto;
    }
}
