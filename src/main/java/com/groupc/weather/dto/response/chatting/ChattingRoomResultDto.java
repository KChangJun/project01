package com.groupc.weather.dto.response.chatting;

import com.groupc.weather.entity.ChattingRoomEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChattingRoomResultDto {
    
    private String roomId;
    private Integer userNumber;

    public ChattingRoomResultDto(ChattingRoomEntity chattingRoomEntity) {
        this.roomId = chattingRoomEntity.getRoomId();
        this.userNumber = chattingRoomEntity.getUserNumber();
    }

}
