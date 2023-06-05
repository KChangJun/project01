package com.groupc.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.groupc.weather.entity.QnaBoardEntity;
import com.groupc.weather.entity.resultSet.QnaBoardListResultSet;
@Repository
public interface QnaBoardRepository extends JpaRepository<QnaBoardEntity, Integer> {
    public boolean existsByBoardNumber(int qnaboardNumber);
    public QnaBoardEntity findByBoardNumber(int qnaBoardNumber);
    
    @Transactional
    void deleteByBoardNumber(int boardNumber);

    @Query(
        value = 
        "SELECT " + 
        "Q.board_number AS boardNumber," +
        "Q.title AS boardTitle," +
        "Q.write_datetime AS boardWriteDatetime," +
        "U.user_number AS boardWriterNumber," +
        "U.nickname AS boardWriterNickname," +
        "U.profile_image_url AS boardWriterProfileImageUrl," +
        "Q.reply_complete AS replyComplete " +
        "FROM User U, Qna_Board Q " +
        "WHERE Q.user_number = U.user_number " +
        "GROUP BY Q.board_number " +
        "ORDER BY Q.write_datetime DESC ",
        nativeQuery = true
    )
    public List<QnaBoardListResultSet> getQnaBoardList();

    @Query(
        value = 
        "SELECT " +
        "Q.board_number AS boardNumber, " + 
        "Q.title AS boardTitle, " +
        "Q.write_datetime AS boardWriteDatetime, " +
        "U.user_number AS boardWriterNumber, " +
        "U.nickname AS boardWriterNickname, " +    
        "U.profile_image_url AS boardWriterProfileImageUrl, " +
        "Q.reply_complete AS replyComplete " +
        "FROM Qna_Board Q, User U " +
        "WHERE (Q.title LIKE CONCAT('%',:search_word,'%') or Q.content LIKE CONCAT ('%',:search_word,'%')) " +
        "AND Q.user_number = U.user_number " +
        "GROUP BY Q.board_number " +
        "ORDER BY Q.write_datetime DESC",
        nativeQuery = true
    )
    public List<QnaBoardListResultSet> getQnaBoardSearchList(@Param("search_word") String searchWord);
}
