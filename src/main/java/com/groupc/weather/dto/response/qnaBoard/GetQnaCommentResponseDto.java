package com.groupc.weather.dto.response.qnaBoard;

import java.util.List;

import com.groupc.weather.dto.ResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetQnaCommentResponseDto extends ResponseDto {

    private List<QnaComment> qnaCommentList;

}


class QnaComment{
    private int qnaCommentNumber;
    private int qnaBoardNumber;
    private int userNumber;
    private int managerNumber;
    private String userProfileImageUrl;
    private String managerProfileImageUrl;
    private String userNickname;
    private String managerNickname;
    private String content;
    private String writeDatetime;
}