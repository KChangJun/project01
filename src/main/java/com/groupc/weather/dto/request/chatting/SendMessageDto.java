package com.groupc.weather.dto.request.chatting;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.groupc.weather.entity.primaryKey.ChattingRoomPk;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendMessageDto {
    @NotNull
    private Integer userNumber;
    @NotNull
    private ChattingRoomPk chattingRoomPk; // roomId 말고 이걸 불러와야 하나
    @NotBlank
    private String message;
}
