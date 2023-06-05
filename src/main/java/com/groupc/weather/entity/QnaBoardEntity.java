package com.groupc.weather.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.groupc.weather.dto.request.qnaBoard.PostQnaBoardRequestDto;
import com.groupc.weather.dto.request.qnaBoard2.PostQnaBoardRequestDto2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "QnaBoard")
@Table(name = "Qna_Board")
public class QnaBoardEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardNumber;
    private String title;
    private String content;
    private String writeDatetime;
    private Integer userNumber;
    private String imageUrl;
    private boolean replyComplete;

    public QnaBoardEntity(PostQnaBoardRequestDto2 dto, Integer userNumber) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);
        this.userNumber = userNumber;
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.writeDatetime = writeDatetime;
        this.imageUrl = dto.getImageUrl();
        this.replyComplete = false;
    }
    
    public QnaBoardEntity(PostQnaBoardRequestDto dto) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);
        this.userNumber = dto.getUserNumber();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.writeDatetime = writeDatetime;
        this.imageUrl = dto.getImageUrl();
        this.replyComplete = false;
    }
}
