package com.groupc.weather.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.groupc.weather.dto.request.qnaBoard.PatchQnaBoardRequestDto;
import com.groupc.weather.dto.request.qnaBoard.PatchQnaCommentRequestDto;
import com.groupc.weather.dto.request.qnaBoard.PostQnaCommentRequestDto2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="QnaComment")
@Table(name="Qna_Comment")
public class QnaCommentEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int qnaCommentNumber;
        private int qnaBoardNumber;
        private String content;
        private String writeDatetime;
        private Integer userNumber;
        private Integer managerNumber;
        private String userNickname;
        private String managerNickname;
        private String userProfileImageUrl;
        private String managerProfileImageUrl;


        public QnaCommentEntity(PostQnaCommentRequestDto2 dto,UserEntity userEntity){
        Date now = new Date(); // 시간 데이터 타입 변수 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//시간 데이터 타입 형태 설정
        String writeDatetime = simpleDateFormat.format(now);//형태안에 데이터타입 변수 넣음

        this.qnaBoardNumber=dto.getQnaBoardNumber();
        this.content=dto.getQnaCommentContent();
        this.writeDatetime=writeDatetime;
        this.userNumber=userEntity.getUserNumber();
        this.managerNumber=null;
        this.userNickname=userEntity.getNickname();
        this.managerNickname=null;
        this.userProfileImageUrl=userEntity.getProfileImageUrl();
        this.managerProfileImageUrl=null;
        }

        public QnaCommentEntity(PostQnaCommentRequestDto2 dto,ManagerEntity managerEntity){
                Date now = new Date(); // 시간 데이터 타입 변수 설정
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//시간 데이터 타입 형태 설정
                String writeDatetime = simpleDateFormat.format(now);//형태안에 데이터타입 변수 넣음
        
                this.qnaBoardNumber=dto.getQnaBoardNumber();
                this.content=dto.getQnaCommentContent();
                this.writeDatetime=writeDatetime;
                this.userNumber=null;
                this.managerNumber=managerEntity.getManagerNumber();
                this.userNickname=null;
                this.managerNickname=managerEntity.getNickname();
                this.userProfileImageUrl=null;
                this.managerProfileImageUrl=managerEntity.getProfileImageUrl();
                }

     
}
