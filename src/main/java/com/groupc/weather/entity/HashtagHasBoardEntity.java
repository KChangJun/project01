package com.groupc.weather.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import com.groupc.weather.entity.primaryKey.HashPk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "HashtagHasBoard")
@Table(name = "Hashtag_Has_Board")
@IdClass(HashPk.class)
public class HashtagHasBoardEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hashtagNumber;
    private int boardNumber;
    //private String hashtagContent;
    //private List<HashListEntity> hashList;
  
    // HashListEntity(PostBoardRequestDto dto){
    //     this.boardNumber = dto.getBoardNumber();
    // }
    // List<HashListEntity> HashListEntitiy(PostPhotoBoardRequestDto dto){}   연구해봅시다...
    //hashList.add(hashtageContent);

}
