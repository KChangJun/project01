package com.groupc.weather.dto.request.board;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.groupc.weather.entity.ImageUrlEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchBoardRequestDto {
    @NotNull
    private Integer boardWriteUserNumber;  //현재 v2에 필요..
    @NotBlank
    private List<String> boardHashtag; //현재 v2에 필요..

    @NotNull
    private Integer boardNumber;  
    @NotBlank
    private String boardTitle;
    @NotBlank
    private String boardContent;

    // 수정할 거 
    private List<ImageUrlEntity> imageUrlList;    //ImageUrlDto 에서 변경했음,,,
    private List<HashtagDto> hashtagList;

    // 추가할 거
    private List<String> addImageUrlList;
    private List<String> addHashtageContent;

    // 삭제할 거
    private List<Integer> deleteImageNumber;
    private List<Integer> deleteHashtageNumber;
}
class HashtagList{
    Integer hashtagNumber;
    String hashtagContent;
}