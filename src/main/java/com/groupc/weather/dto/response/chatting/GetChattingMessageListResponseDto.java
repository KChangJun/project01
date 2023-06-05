package com.groupc.weather.dto.response.chatting;

import java.util.ArrayList;
import java.util.List;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.entity.resultSet.ChattingMessageListResultSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class GetChattingMessageListResponseDto extends ResponseDto {
    
    private List<ChattingMessageSummary> chattingMessageList;

    public GetChattingMessageListResponseDto(List<ChattingMessageListResultSet> resultSet) {
        super("SU", "Success");

        List<ChattingMessageSummary> chattingMessageList = new ArrayList<>();

        for (ChattingMessageListResultSet result: resultSet) {
            ChattingMessageSummary chattingMessageSummary = new ChattingMessageSummary(result);

            chattingMessageList.add(chattingMessageSummary);
        }

        this.chattingMessageList = chattingMessageList;
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ChattingMessageSummary {
    public String roomId;
    public Integer userNumber;
    public String userNickname;
    public String userProfileImageUrl;
    public String date;
    public String message;
    public int view;

    public ChattingMessageSummary(ChattingMessageListResultSet resultSet){
        this.roomId = resultSet.getRoomId();
        this.userNumber = resultSet.getUserNumber();
        this.userNickname = resultSet.getUserNickname();
        this.userProfileImageUrl = resultSet.getUserProfileImageUrl();
        this.date = resultSet.getDate();
        this.message = resultSet.getMessage();
        this.view = resultSet.getView();
    }
}

