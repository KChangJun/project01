package com.groupc.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.entity.resultSet.GetTop5FollowerListResult;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public boolean existsByEmail(String email);

    public boolean existsByPassword(String password);

    public boolean existsByUserNumber(Integer userNumber);

    public boolean existsByNickname(String nickname);

    public boolean existsByPhoneNumber(String phoneNumber);


    public UserEntity findByUserNumber(int userNumber);

    public UserEntity findByEmail(String email);

    public UserEntity findByName(String name);

    public UserEntity findByUserNumber(Integer userNumber);

    public UserEntity findByPassword(String password);

      // Top5 받아오는 쿼리
      @Query(value = "SELECT " +
      "U.user_number AS userNumber," +
      "U.nickname AS nickname," +
      "U.profile_image_url AS profileImageUrl," +
      "count(DISTINCT F.follower_number) AS followerCount " +
      "FROM User U, Following F " +
      "WHERE U.user_number = F.following_number " +
      "GROUP BY U.user_number " +
      "ORDER BY followerCount DESC " +
      "LIMIT 5", nativeQuery = true)
public List<GetTop5FollowerListResult> getTop5ListBy();

}
