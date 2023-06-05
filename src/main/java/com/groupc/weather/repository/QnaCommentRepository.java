package com.groupc.weather.repository;

import java.util.List;

import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.weather.entity.QnaCommentEntity;


@Repository
public interface QnaCommentRepository extends JpaRepository<QnaCommentEntity, Integer> {
    
    public QnaCommentEntity findByQnaCommentNumber(Integer qnaCommentNumber);
    public boolean existsByQnaCommentNumber(Integer qnaCommentNumber);
    @Transactional
    void deleteByQnaCommentNumber(int qnaCommentNumber);

    @Transactional
    void deleteByQnaBoardNumber(int qnaBoardNumber);

    public List<QnaCommentEntity> findByQnaBoardNumber(int qnaBoardNumber);
}
