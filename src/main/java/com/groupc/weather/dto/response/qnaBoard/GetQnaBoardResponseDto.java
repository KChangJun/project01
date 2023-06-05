package com.groupc.weather.dto.response.qnaBoard;

import java.util.ArrayList;
import java.util.List;

import com.groupc.weather.dto.ResponseDto;

import com.groupc.weather.entity.QnaBoardEntity;
import com.groupc.weather.entity.QnaCommentEntity;
import com.groupc.weather.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetQnaBoardResponseDto extends ResponseDto {
    private int qnaBoardNumber;
    private String title;
    private String content;
    private String imageUrl;
    private String writeDatetime;
    private String userNickname;
    private String userProfileImageUrl;
    private List<Comment> commentList;
    private int commentCount;

    public GetQnaBoardResponseDto(
        QnaBoardEntity qnaBoardEntity, UserEntity userEntity,
        List<QnaCommentEntity> qnaCommentEntities
    ) {
        super("SU", "Success");

        this.qnaBoardNumber = qnaBoardEntity.getBoardNumber();
        this.title = qnaBoardEntity.getTitle();
        this.content = qnaBoardEntity.getContent();
        this.imageUrl = qnaBoardEntity.getImageUrl();
        this.writeDatetime = qnaBoardEntity.getWriteDatetime();
        this.userNickname = userEntity.getNickname();
        this.userProfileImageUrl = userEntity.getProfileImageUrl();
        this.commentList = Comment.createList(qnaCommentEntities);
        this.commentCount = commentList.size();
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Comment {
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

    Comment(QnaCommentEntity qnaCommentEntity) {
        this.qnaCommentNumber = qnaCommentEntity.getQnaCommentNumber();
        this.qnaBoardNumber = qnaCommentEntity.getQnaBoardNumber();
        this.userNumber = qnaCommentEntity.getUserNumber();
        this.managerNumber = qnaCommentEntity.getManagerNumber();
        this.userProfileImageUrl = qnaCommentEntity.getUserProfileImageUrl();
        this.managerProfileImageUrl = qnaCommentEntity.getManagerProfileImageUrl();
        this.userNickname = qnaCommentEntity.getUserNickname();
        this.managerNickname = qnaCommentEntity.getManagerNickname();
        this.content = qnaCommentEntity.getContent();
        this.writeDatetime = qnaCommentEntity.getWriteDatetime();
    }

    static List<Comment> createList(List<QnaCommentEntity> qnaCommentEntities) {
        List<Comment> commentList = new ArrayList<>();
        for (QnaCommentEntity qnaBoardCommentEntity: qnaCommentEntities) {
            Comment comment = new Comment(qnaBoardCommentEntity);
            commentList.add(comment);
        }
        return commentList;
    }
}
