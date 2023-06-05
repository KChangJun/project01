package com.groupc.weather.dto.response.user;

import java.util.ArrayList;
import java.util.List;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.entity.BoardEntity;
import com.groupc.weather.entity.CommentEntity;
import com.groupc.weather.entity.FollowingEntity;
import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.entity.resultSet.GetFollowerListResultSet;
import com.groupc.weather.entity.resultSet.GetFollowingListResultSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponseDto extends ResponseDto {
    private String userName;
    private int userNumber;
    private String userNickname;
    private String userProfileImageUrl;
    private String userGender;
    private List<FollowerList> userFollowerList;
    private int userFollowerCount;
    private List<FollowingList> userFollowingList;
    private int userFollowingCount;
    private int boardCount;
    private int commentCount;

    // public GetUserResponseDto(
    // UserEntity userEntity, BoardEntity boardEntity, CommentEntity commentEntity,
    // List<FollowingEntity> followingEntities, List<FollowerEntity>
    // followerEntities) {
    // super("SU", "Success");

    // this.userNumber = userEntity.getUserNumber();
    // this.userNickname = userEntity.getNickname();
    // this.userProfileImageUrl = userEntity.getProfileImageUrl();
    // this.userGender = userEntity.getGender();
    // this.boardCount = boardEntity.getViewCount();
    // this.commentCount = commentEntity.getCommentCount();

    // this.userFollowerList = Follower.createList(followerEntities);
    // this.userFollowerCount = followerEntities.getFollowerCount();
    // this.userFollowingList = Following.createList(followingEntities);
    // this.userFollowingCount = followingEntities.getFollowingCount();
    // }

    public GetUserResponseDto(
            UserEntity userEntity, List<GetFollowerListResultSet> getFollowerListResultSets,
            List<GetFollowingListResultSet> getFollowingListResultSet) {
        super("SU", "Success");

        List<FollowerList> followerList = new ArrayList<>();
        List<FollowingList> followingList = new ArrayList<>();

        for (GetFollowerListResultSet ListResult : getFollowerListResultSets) {
            FollowerList list = new FollowerList(ListResult);
            followerList.add(list);
        }

        for (GetFollowingListResultSet ListResult : getFollowingListResultSet) {
            FollowingList list = new FollowingList(ListResult);
            followingList.add(list);
        }

        this.userFollowerList = followerList;
        this.userFollowerCount = followerList.size();
        this.userFollowingList = followingList;
        this.userFollowingCount = followingList.size();
        this.userName = userEntity.getName();
        this.userNumber = userEntity.getUserNumber();
        this.userNickname = userEntity.getNickname();
        this.userProfileImageUrl = userEntity.getProfileImageUrl();
        this.userGender = userEntity.getGender();
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class BoardCount {
    private int userNumber;
    private int boardNumber;

    BoardCount(BoardEntity boardEntity, UserEntity userEntity) {
        this.userNumber = userEntity.getUserNumber();
        this.boardNumber = boardEntity.getBoardNumber();
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CommentCount {
    private int userNumber;
    private int commentNumber;

    CommentCount(CommentEntity commentEntity, UserEntity userEntity) {
        this.userNumber = userEntity.getUserNumber();
        this.commentNumber = commentEntity.getBoardNumber();
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class FollowerList {
    private int followerUserNumber;
    private String followerNickname;
    private String followerProfileImageUrl;

    FollowerList(GetFollowerListResultSet getFollowerListResultSet) {
        this.followerUserNumber = getFollowerListResultSet.getUserNumber();
        this.followerNickname = getFollowerListResultSet.getUserNickname();
        this.followerProfileImageUrl = getFollowerListResultSet.getUserProfileImageUrl();
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class FollowingList {
    private int followingUserNumber;
    private String followingNickname;
    private String followingProfileImageUrl;

    FollowingList(GetFollowingListResultSet getFollowingListResultSet) {
        this.followingUserNumber = getFollowingListResultSet.getUserNumber();
        this.followingNickname = getFollowingListResultSet.getUserNickname();
        this.followingProfileImageUrl = getFollowingListResultSet.getUserProfileImageUrl();
    }
}
