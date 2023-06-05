package com.groupc.weather.dto.response.user;

import java.util.ArrayList;
import java.util.List;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.entity.FollowingEntity;
import com.groupc.weather.entity.resultSet.GetFollowingListResultSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class FollowingUserResponseDto extends ResponseDto {
    private List<FollowingList> followingUserList;
    private Integer count;

    public FollowingUserResponseDto(List<GetFollowingListResultSet> getFollowingListResultSet) {

        super("SU", "Success");

        List<FollowingList> followingList = new ArrayList<>();

        for (GetFollowingListResultSet ListResult : getFollowingListResultSet) {
            FollowingList list = new FollowingList(ListResult);
            followingList.add(list);
        }
        this.followingUserList = followingList;
        this.count = followingList.size();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class FollowingList {
        private Integer userNumber;
        private String nickname;
        private String profileImageUrl;

        public FollowingList(GetFollowingListResultSet getFollowingListResultSet) {
            this.userNumber = getFollowingListResultSet.getUserNumber();
            this.nickname = getFollowingListResultSet.getUserNickname();
            this.profileImageUrl = getFollowingListResultSet.getUserProfileImageUrl();
        }
    }
}
