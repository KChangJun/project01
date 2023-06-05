package com.groupc.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groupc.weather.entity.FollowingEntity;
import com.groupc.weather.entity.primaryKey.FollowingPk;
import com.groupc.weather.entity.resultSet.GetFollowerListResultSet;
import com.groupc.weather.entity.resultSet.GetFollowingListResultSet;
//import com.groupc.weather.entity.resultSet.GetTop5FollowerListResult;

@Repository
public interface FollowRepository extends JpaRepository<FollowingEntity, FollowingPk> {

        // public List<GetTop5FollowerListResult> getTop5ListBy();

        // public List<GetFollowingListResultSet> findByFollowingNumber(Integer
        // followingNumber);

        public List<FollowingEntity> findByFollowingNumber(Integer followingNumber);

        public List<FollowingEntity> findByFollowerNumber(Integer followerNumber);

        @Query(value = "SELECT " +
                        "U.user_number AS UserNumber," +
                        "U.nickname AS UserNickname," +
                        "U.profile_image_url AS UserProfileImageUrl " +
                        "FROM User U " +
                        "WHERE U.user_number " +
                        "IN(SELECT follower_number " +
                        "FROM following " +
                        "WHERE following_number = :following_number) ", nativeQuery = true)
        public List<GetFollowerListResultSet> getFollowerUserList(@Param("following_number") Integer followingNumber);

        @Query(value = "SELECT " +
                        "U.user_number AS UserNumber," +
                        "U.nickname AS UserNickname," +
                        "U.profile_image_url AS UserProfileImageUrl " +
                        "FROM User U " +
                        "WHERE U.user_number " +
                        "IN(SELECT following_number " +
                        "FROM following " +
                        "WHERE follower_number = :follower_number) ", nativeQuery = true)
        public List<GetFollowingListResultSet> getFollowingUserList(@Param("follower_number") Integer followerNumber);
}
