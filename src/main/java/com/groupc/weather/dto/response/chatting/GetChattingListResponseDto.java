package com.groupc.weather.dto.response.chatting;

import java.util.ArrayList;
import java.util.List;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.entity.resultSet.ChattingListResultSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class GetChattingListResponseDto extends ResponseDto {

    private List<ChattingListSummary> chattingList;

    public GetChattingListResponseDto(List<ChattingListResultSet> resultSet) {
        super("SU", "Success");

        List<ChattingListSummary> chattingList = new ArrayList<>();

        for (ChattingListResultSet result: resultSet) {
            ChattingListSummary chattingListSummary = new ChattingListSummary(result);

            chattingList.add(chattingListSummary);
        }

        this.chattingList = chattingList;
    }
    
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ChattingListSummary {
    public String roomId;
    public Integer userNumber;
    public String userNickname;
    public String userProfileImageUrl;
    public String date;
    public String message;
    public Integer viewCount;

    public ChattingListSummary(ChattingListResultSet resultSet){
        this.roomId = resultSet.getRoomId();
        this.userNumber = resultSet.getUserNumber();
        this.userNickname = resultSet.getUserNickname();
        this.userProfileImageUrl = resultSet.getUserProfileImageUrl();
        this.date = resultSet.getDate();
        this.message = resultSet.getMessage();
        this.viewCount = resultSet.getViewCount();
    }
}
