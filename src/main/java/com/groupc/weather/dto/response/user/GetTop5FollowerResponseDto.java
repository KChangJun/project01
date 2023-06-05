package com.groupc.weather.dto.response.user;

import java.util.ArrayList;
import java.util.List;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.entity.resultSet.GetTop5FollowerListResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class GetTop5FollowerResponseDto extends ResponseDto {

    private List<FollowerSummary> followerTop5List;

    public GetTop5FollowerResponseDto(List<GetTop5FollowerListResult> resultSet) {
        super("SU", "Success");

        List<FollowerSummary> followerTop5List = new ArrayList<>();

        // 받아온 TOP5 전부 더해서 리스트에 저장.
        for (GetTop5FollowerListResult result : resultSet) {
            FollowerSummary followerSummary = new FollowerSummary(result);
            followerTop5List.add(followerSummary);
        }
        this.followerTop5List = followerTop5List;
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class FollowerSummary {
    private Integer userNumber;
    // private Integer followCount;
    private String nickname;
    private String profileImageUrl;

    public FollowerSummary(GetTop5FollowerListResult resultSet) {
        this.userNumber = resultSet.getUserNumber();
        // this.followCount = resultSet.getFollowCount();
        this.nickname = resultSet.getNickname();
        this.profileImageUrl = resultSet.getProfileImageUrl();
    }
}
