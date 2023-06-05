package com.groupc.weather.dto.response.user;

import java.util.ArrayList;
import java.util.List;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.entity.FollowingEntity;
import com.groupc.weather.entity.resultSet.GetFollowerListResultSet;
import com.groupc.weather.repository.FollowRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class FollowerUserResponseDto extends ResponseDto {
    private List<FollowerList> followerUserList;
    private Integer count;

    public FollowerUserResponseDto(List<GetFollowerListResultSet> getFollowerListResultSet) {

        super("SU", "Success");

        List<FollowerList> followerList = new ArrayList<>();

        for (GetFollowerListResultSet ListResult : getFollowerListResultSet) {
            FollowerList list = new FollowerList(ListResult);
            followerList.add(list);
        }
        this.followerUserList = followerList;
        this.count = followerList.size();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class FollowerList {
        private Integer userNumber;
        private String nickname;
        private String profileImageUrl;

        public FollowerList(GetFollowerListResultSet getFollowerListResultSet) {
            this.userNumber = getFollowerListResultSet.getUserNumber();
            this.nickname = getFollowerListResultSet.getUserNickname();
            this.profileImageUrl = getFollowerListResultSet.getUserProfileImageUrl();
        }
    }
}
