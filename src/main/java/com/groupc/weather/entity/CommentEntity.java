package com.groupc.weather.entity;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.groupc.weather.dto.request.board.PostCommentRequestDto2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Comment")
@Entity(name="Comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer commentNumber;
    private Integer userNumber;
    private Integer managerNumber;
    private Integer boardNumber;
    private String userNickname;
    private String managerNickname;
    private String content;
    private String writeDatetime;
    private String userProfileImageUrl;
    private String managerProfileImageUrl;

    //가지고 있는 코멘트의 정보
    public CommentEntity(PostCommentRequestDto2 dto, UserEntity userEntity) {

        Date now = new Date();
        SimpleDateFormat simpleDateFormat = 
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        this.userNumber = userEntity.getUserNumber();
        this.boardNumber = dto.getBoardNumber();
        this.userNickname = userEntity.getNickname();
        this.userProfileImageUrl = userEntity.getProfileImageUrl();
        this.content = dto.getCommentContent();
        this.writeDatetime = writeDatetime;
    }

    public CommentEntity(PostCommentRequestDto2 dto, ManagerEntity managerEntity) {

        Date now = new Date();
        SimpleDateFormat simpleDateFormat = 
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        this.userNumber = managerEntity.getManagerNumber();
        this.boardNumber = dto.getBoardNumber();
        this.userNickname = managerEntity.getNickname();
        this.userProfileImageUrl = managerEntity.getProfileImageUrl();
        this.content = dto.getCommentContent();
        this.writeDatetime = writeDatetime;
    }
}