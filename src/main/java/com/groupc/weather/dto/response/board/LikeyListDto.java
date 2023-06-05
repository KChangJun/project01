package com.groupc.weather.dto.response.board;

import com.groupc.weather.entity.LikeyEntity;
import com.groupc.weather.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeyListDto extends LikeyEntity{
    private String userNickname;
    private String userProfileImageUrl;


    public LikeyListDto(LikeyEntity likeyEntity,UserEntity userEntity){
        super(likeyEntity.getUserNumber(), likeyEntity.getBoardNumber());
        this.userNickname=userEntity.getNickname();
        this.userProfileImageUrl=userEntity.getProfileImageUrl();
    }
}
