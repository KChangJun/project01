package com.groupc.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groupc.weather.entity.BoardEntity;
import com.groupc.weather.entity.resultSet.BoardCommentLikeyCountResultSet;
import com.groupc.weather.entity.resultSet.BoardCommentResultSet;
import com.groupc.weather.entity.resultSet.GetBoardListResult;

import com.groupc.weather.entity.resultSet.LikeyResultSet;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    public BoardEntity findByBoardNumber(Integer boardNumber);
    public boolean existsByBoardNumber(Integer boardNumber);

    public List<BoardEntity> findByUserNumber(Integer userNumber);

    @Query(value =
    "SELECT " + 
    "B.board_number AS boardNumber, " +  
    "C.comment_number AS commentNumber, " +
    "C.user_number AS userNumber, " +  
    "C.content AS commentContent, " + 
    "C.write_datetime AS commentDatetime, " + 
    "C.user_nickname AS commentUserNickname, " +
    "C.user_profile_image_url AS commentUserProfileImageUrl, "+ 
    "FROM Board B, Comment C " +
    "Where B.board_number = C.board_number " +
    "GROUP BY B.board_number " +
    "ORDER BY C.write_datetime DESC; ",
    nativeQuery = true
)
public List<BoardCommentResultSet> getBoardCommentList();


@Query(value =
"SELECT " + 
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriterDatetime, " +
"B.weather_description AS weatherDescription, " +
"B.temperature AS temperature, " +
"B.view_count AS viewCount, " +
"U.nickname AS writerNickname, " +
"U.profile_image_url AS writerProfileImageUrl, " +
"count(DISTINCT C.comment_number) AS commentCount, " +
"count(DISTINCT L.user_number) AS likeCount " +
"FROM Board B, Comment C, Likey L, User U "  +
"Where B.board_number= C.board_number, " +
"AND B.user_number = U.user_number " +
"AND B.board_number = L.board_number " +
"GROUP BY B.board_number; ",
nativeQuery = true
)
public List<BoardCommentLikeyCountResultSet> getBoardCommentLikeyList();


// 5.게시물 조회
@Query(value=
"SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"BF.image_url AS boardFirstImageUrl, " +
"B.write_datetime AS boardWriteDatetime, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"count(L.user_number) AS likeCount " +
"FROM Board B "+
"LEFT JOIN (SELECT * FROM( " +
"select image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC;" ,
nativeQuery = true
)
public List<GetBoardListResult> getBoardList();


// 3. 본인작성 게시물 목록 쿼리문
@Query(value=
"SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"count(L.user_number) AS likeCount " +
"FROM Board B " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.user_number = C.board_number " +
"LEFT JOIN likey L " +
"ON B.user_number = L.board_number " +
"WHERE B.user_number = :user_number " +
"GROUP BY B.board_number " +
"ORDER BY B.write_datetime DESC;",
nativeQuery = true
)
public List<GetBoardListResult> getMyBoardList(@Param("user_number") int userNumber);


// 4.게시물 목록 조회(좋아요 top5)
// 쿼리문 작성하기!!! List는 ORDER BY boardWriteDatetime DESC
// Top5는  LIST 쿼리문에서  + ORDER BY viewCount , LIMIT 5 하면댐
@Query(value=
"SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"count(L.user_number) AS likeCount " +
"FROM Board B "+
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.user_number = C.board_number " +
"LEFT JOIN likey L " +
"ON B.user_number = L.board_number " +
"GROUP BY B.board_number " +
"ORDER BY B.write_datetime DESC Limit 5;",
nativeQuery = true
)
public List<GetBoardListResult> getBoardListTop5();


//6.첫화면 8개게시물
// top 5 에서 limit을 8개로 바꾸고 , 화면 첫 사진만 보고 게시물 번호만 이두개만 나타냄
// boardNumber , boardFisrtImageUrl , Limit 8 , ORDER BY writeDateTime DESC 
@Query(value=
"SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"count(L.user_number) AS likeCount " +
"FROM Board B "+
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.user_number = C.board_number " +
"LEFT JOIN likey L " +
"ON B.user_number = L.board_number " +
"GROUP BY B.board_number " +
"ORDER BY B.write_datetime DESC Limit 8;",
nativeQuery = true
)
public List<GetBoardListResult> getBoardFirstView();

//게시물 조회 했을 때 보여지는 첫화면
@Query(value=
"Select " +
"I.image_url AS boardFirstImageUrl " +
"From Board B, Image_Url I " +
"Where B.board_number = I.board_number " +
"AND B.board_number = :board_number " +
"ORDER BY I.image_number Asc " +
"limit 1;",
nativeQuery = true
)
public String getBoardFirstImageUrl(@Param("board_number") int boardNumber);



// 8.특정 게시물 삭제
// public Integer deleteBoardLike(Integer userNumber, Integer boardNumber);


// 9.특정 게시물 좋아요
@Query(value =
"SELECT " + 
"B.board_number AS boardNumber, " +
"L.user_number AS userNumber, " +
"L.user_nickname AS userNickname, " +
"L.user_profile_image_url AS commentUserProfileImageUrl, "+
"GROUP BY B.board_number ",
nativeQuery = true
)
public List<LikeyResultSet> getLikeList();


//11.특정 유저 좋아요 게시물 조회
@Query(value=
"SELECT B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"BF.image_url AS boardFirstImageUrl, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"LC.likeCount AS likeCount " +
"FROM Board B " +
"LEFT JOIN (SELECT * FROM " +
"(select image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Likey L " +
"ON B.board_number = L.board_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN ( " +
"Select B.board_number,count(L.user_number) AS likeCount FROM Board B " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number) AS LC " +
"ON LC.board_number = B.board_number " +
"WHERE L.user_number =:user_number " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC; ",
nativeQuery = true
)
public List<GetBoardListResult> getLikeBoardList(@Param("user_number") int userNumber);

// 12. 특정 게시물 검색
@Query(value=
"SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"BF.image_url AS boardFirstImageUrl, " +
"U.nickname AS boardWriterNickname, " + 
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"LC.likeCount AS likeCount " + 
"FROM Board B " +
"LEFT JOIN (SELECT * FROM( " +
"select image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN Likey L " +
"ON B.board_number = L.board_number " +
"LEFT JOIN ( " +
"Select B.board_number,count(L.user_number) AS likeCount FROM Board B " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number) AS LC " +
"ON LC.board_number = B.board_number " +
"WHERE (B.title LIKE CONCAT('%',:search_word,'%') " +
"or B.content LIKE CONCAT ('%',:search_word,'%') " +
"OR B.weather_info Like CONCAT('%',:search_word,'%') " +
"OR B.temperature Like CONCAT('%',:search_word,'%')) " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC; ",
nativeQuery = true
)
public List<GetBoardListResult> getSearchListByWord(@Param("search_word") String searchWord);

@Query(
value = 
"SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"BF.image_url AS boardFirstImageUrl, " +
"U.nickname AS boardWriterNickname, " + 
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"LC.likeCount AS likeCount " + 
"FROM Board B " +
"LEFT JOIN (SELECT * FROM( " +
"select image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN Likey L " +
"ON B.board_number = L.board_number " +
"LEFT JOIN ( " +
"Select B.board_number,count(L.user_number) AS likeCount FROM Board B " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number) AS LC " +
"ON LC.board_number = B.board_number " +
"WHERE (B.title LIKE CONCAT('%',:search_word,'%') " +
"OR B.content LIKE CONCAT ('%',:search_word,'%')) " +
"AND (B.weather_main LIKE CONCAT('%', :weather, '%')) " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC; ",
nativeQuery = true
    )
public List<GetBoardListResult> getSearchListByWordAndWeather(@Param("search_word") String searchWord,
                                                                  @Param("weather") String weather);

@Query(value = "SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"BF.image_url AS boardFirstImageUrl, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"LC.likeCount AS likeCount " +
"FROM Board B " +
"LEFT JOIN (SELECT * FROM( " +
"select image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN Likey L " +
"ON B.board_number = L.board_number " +
"LEFT JOIN ( " +
"Select B.board_number,count(L.user_number) AS likeCount FROM Board B " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number) AS LC " +
"ON LC.board_number = B.board_number " +
"WHERE (B.title LIKE CONCAT('%',:search_word,'%') " +
"OR B.content LIKE CONCAT ('%',:search_word,'%')) " +
"AND (B.temperature BETWEEN :minTemperature AND :maxTemperature) " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC; ", nativeQuery = true
)
public List<GetBoardListResult> getSearchListByWordAndTemperatures(@Param("search_word") String searchWord,
                                                                       @Param("minTemperature") Integer minTemperature,
                                                                       @Param("maxTemperature") Integer maxTemperature);                                                                  
                                                                
@Query(value = "SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"BF.image_url AS boardFirstImageUrl, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"LC.likeCount AS likeCount " +
"FROM Board B " +
"LEFT JOIN (SELECT * FROM( " +
"select image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN Likey L " +
"ON B.board_number = L.board_number " +
"LEFT JOIN ( " +
"Select B.board_number,count(L.user_number) AS likeCount FROM Board B " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number) AS LC " +
"ON LC.board_number = B.board_number " +
"WHERE (B.title LIKE CONCAT('%',:search_word,'%') " +
"OR B.content LIKE CONCAT ('%',:search_word,'%')) " +
"AND ((B.temperature BETWEEN :minTemperature AND :maxTemperature) " +
"OR (B.weather_main LIKE CONCAT('%', :weather, '%')) " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC; ", nativeQuery = true
)
public List<GetBoardListResult> getSearchListByWordAndAll(@Param("search_word") String searchWord,
                                                              @Param("weather") String weather,
                                                              @Param("minTemperature") Integer minTemperature,
                                                              @Param("maxTemperature") Integer maxTemperature);

// 13.특정 게시물 검색(해쉬태그)
@Query(value=
"SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"BF.image_url AS boardFirstImageUrl, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"LC.likeCount AS likeCount " +
"FROM Board B " +
"LEFT JOIN (SELECT * FROM( " +
"SELECT image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN ( " +
"Select B.board_number,count(L.user_number) AS likeCount FROM Board B " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number " +
") AS LC " +
"ON LC.board_number = B.board_number " +
"LEFT JOIN ( " +
"SELECT HB.board_number AS board_number " +
"FROM hashtag H, hashtag_has_board HB " +
"WHERE H.hashtag_number = HB.hashtag_number " +
"AND H.hashtag_content LIKE CONCAT('%',:search_word,'%') " +
") AS H " +
"ON B.board_number = H.board_number " +
"WHERE B.board_number = H.board_number " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC; ",
nativeQuery = true
)
public List<GetBoardListResult> getSearchHashtagByWord(@Param("search_word") String searchWord);

@Query(
value=
"SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"BF.image_url AS boardFirstImageUrl, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"LC.likeCount AS likeCount " +
"FROM Board B " +
"LEFT JOIN (SELECT * FROM( " +
"SELECT image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN ( " +
"Select B.board_number,count(L.user_number) AS likeCount FROM Board B " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number " +
") AS LC " +
"ON LC.board_number = B.board_number " +
"LEFT JOIN ( " +
"SELECT HB.board_number AS board_number " +
"FROM hashtag H, hashtag_has_board HB " +
"WHERE H.hashtag_number = HB.hashtag_number " +
"AND H.hashtag_content LIKE CONCAT('%',:search_word,'%') " +
") AS H " +
"ON B.board_number = H.board_number " +
"WHERE B.board_number = H.board_number " +
"AND B.weather_main LIKE CONCAT('%', :weather, '%') " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC; ",
nativeQuery = true
    )
public List<GetBoardListResult> getSearchHashtagByWordAndWeather(@Param("search_word") String searchWord,
                                                                     @Param("weather") String weather);

  
@Query(value = "SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"BF.image_url AS boardFirstImageUrl, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"LC.likeCount AS likeCount " +
"FROM Board B " +
"LEFT JOIN (SELECT * FROM( " +
"SELECT image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN ( " +
"Select B.board_number,count(L.user_number) AS likeCount FROM Board B " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number " +
") AS LC " +
"ON LC.board_number = B.board_number " +
"LEFT JOIN ( " +
"SELECT HB.board_number AS board_number " +
"FROM hashtag H, hashtag_has_board HB " +
"WHERE H.hashtag_number = HB.hashtag_number " +
"AND H.hashtag_content LIKE CONCAT('%',:search_word,'%') " +
") AS H " +
"ON B.board_number = H.board_number " +
"WHERE B.board_number = H.board_number " +
"AND (B.temperature BETWEEN :minTemperature AND :maxTemperature) " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC; ", nativeQuery = true
)
public List<GetBoardListResult> getSearchHashtagByWordAndTemperatures(@Param("search_word") String searchWord,
                                                                          @Param("minTemperature") Integer minTemperature,
                                                                          @Param("maxTemperature") Integer maxTemperature);

@Query(value = "SELECT " +
"B.board_number AS boardNumber, " +
"B.title AS boardTitle, " +
"B.content AS boardContent, " +
"B.write_datetime AS boardWriteDatetime, " +
"BF.image_url AS boardFirstImageUrl, " +
"U.nickname AS boardWriterNickname, " +
"U.profile_image_url AS boardWriterProfileImageUrl, " +
"count(C.comment_number) AS commentCount, " +
"LC.likeCount AS likeCount " +
"FROM Board B " +
"LEFT JOIN (SELECT * FROM( " +
"SELECT image_number, image_url, board_number, " +
"ROW_NUMBER() OVER (PARTITION BY board_number) " +
"AS N FROM image_url) AS T " +
"WHERE T.N=1) AS BF " +
"ON B.board_number = BF.board_number " +
"LEFT JOIN User U " +
"ON B.user_number = U.user_number " +
"LEFT JOIN Comment C " +
"ON B.board_number = C.board_number " +
"LEFT JOIN ( " +
"Select B.board_number,count(L.user_number) AS likeCount FROM Board B " +
"LEFT JOIN likey L " +
"ON B.board_number = L.board_number " +
"GROUP BY B.board_number " +
") AS LC " +
"ON LC.board_number = B.board_number " +
"LEFT JOIN ( " +
"SELECT HB.board_number AS board_number " +
"FROM hashtag H, hashtag_has_board HB " +
"WHERE H.hashtag_number = HB.hashtag_number " +
"AND H.hashtag_content LIKE CONCAT('%',:search_word,'%') " +
") AS H " +
"ON B.board_number = H.board_number " +
"WHERE B.board_number = H.board_number " +
"AND ((B.temperature BETWEEN :minTemperature AND :maxTemperature) " +
"OR (B.weather_main LIKE CONCAT('%', :weather, '%'))) " +
"GROUP BY B.board_number, BF.image_url " +
"ORDER BY B.write_datetime DESC; ",
nativeQuery = true
    )
public List<GetBoardListResult> getSearchHashtagByWordAndAll(@Param("search_word") String searchWord,
                                                                 @Param("weather") String weather,
                                                                 @Param("minTemperature") Integer minTemperature,
                                                                 @Param("maxTemperature") Integer maxTemperature);




}
