package com.groupc.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groupc.weather.entity.ChattingMessageEntity;
import com.groupc.weather.entity.resultSet.ChattingListResultSet;
import com.groupc.weather.entity.resultSet.ChattingMessageListResultSet;
@Repository
public interface ChattingMessageRepository extends JpaRepository<ChattingMessageEntity, Integer> {
    
    @Query(
        value =
        "SELECT * " +
        "FROM chatting_message " +
        "where " +
        "(room_id = :room_id) " +
        "and (user_number = :user_number) " +
        "and view = 0; ",
        nativeQuery = true
    )
    List<ChattingMessageEntity> findByNotViewList(@Param("room_id") String roomId,
                                                  @Param("user_number") Integer userNumber);

    
    @Query(
        value =
        "SELECT " +
        "R.room_id As room_id, " +
        "R.user_number AS user_number, " +
        "U.nickname AS nickname, " +
        "U.profile_image_url AS profile_image_url, " +
        "CM.message AS message, " +
        "CM.date AS date, " +
        "COUNT(CASE WHEN M.view=0 AND M.user_number!=2 THEN 1 END) AS not_view " +
        "FROM chatting_room R " +
        "INNER JOIN User U ON R.user_number = U.user_number " +
        "INNER JOIN ( " +
        "SELECT room_id, message, CM.date " +
        "FROM (SELECT M.room_id AS room_id, M.message, M.view, M.date, ROW_NUMBER() OVER (PARTITION BY room_id ORDER BY date DESC) AS N " +
        "FROM chatting_message AS M) AS CM WHERE CM.N=1) AS CM " +
        "ON R.room_id = CM.room_id " +
        "LEFT JOIN chatting_message M ON (M.room_id = R.room_id) AND (M.view = 1) " +
        "LEFT JOIN chatting_room AS R2 " +
        "ON R2.room_id = R.room_id " +
        "WHERE NOT R.user_number = 2 AND R2.user_number = 2 " +
        "GROUP BY R.room_id; ",
        nativeQuery = true
    )
    List<ChattingListResultSet> getChattingList(@Param("user_number") Integer userNumber);

    // @Query(
    //     value =
    //     "SELECT " +
    //     "R.room_id As room_id, " +
    //     "U.user_number AS user_number, " +
    //     "U.nickname AS nickname, " +
    //     "U.profile_image_url AS profile_image_url, " +
    //     "CM.message AS message, " +
    //     "CM.date AS date, " +
    //     "V.not_view AS not_view " +
    //     "FROM chatting_room R " +
    //     "LEFT JOIN User U " +
    //     "ON R.user_number = U.user_number " +
    //     "LEFT JOIN ( " +
    //     "SELECT room_id, message, CM.date " +
    //     "FROM (SELECT M.room_id AS room_id, M.message, M.view, M.date, ROW_NUMBER() OVER (PARTITION BY room_id ORDER BY date DESC) AS N " +
    //     "FROM chatting_message AS M) AS CM WHERE CM.N=1) AS CM " +
    //     "ON R.room_id = CM.room_id " +
    //     "LEFT JOIN (SELECT room_id, count(view) AS not_view FROM chatting_message WHERE view = 0 GROUP BY room_id) AS V " +
    //     "ON V.room_id = R.room_id " +
    //     "LEFT JOIN chatting_room AS R2 " +
    //     "ON R2.room_id = R.room_id " +
    //     "WHERE NOT R.user_number = :user_number AND R2.user_number = :user_number " +
    //     "GROUP BY R.room_id; ",
    //     nativeQuery = true
    // )
    // List<ChattingListResultSet> getChattingList(@Param("user_number") Integer userNumber);

    @Query(
        value =
        "SELECT " +
        "R.room_id AS roomId, " +
        "U.nickname AS nickname, " +
        "M.user_number AS userNumber, " +
        "M.message AS message, " +
        "U.profile_image_url AS profileImageUrl, " +
        "M.date AS date, " +
        "M.view AS view " +
        "FROM Chatting_Room R " +
        "LEFT JOIN ( " +
        "SELECT * FROM Chatting_Message " +
        "WHERE user_number = :user_number OR room_id = :room_id) M " +
        "ON R.room_id = M.room_id " +
        "LEFT JOIN User U ON M.user_number = U.user_number " +
        "WHERE R.user_number = :user_number AND R.room_id = :room_id " +
        "ORDER BY M.date ASC; ", 
        nativeQuery = true  
    )
    List<ChattingMessageListResultSet> getChattingMessageList(
        @Param("user_number") Integer userNumber, 
        @Param("room_id") String roomId);


}
