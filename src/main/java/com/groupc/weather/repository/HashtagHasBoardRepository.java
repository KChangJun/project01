package com.groupc.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.groupc.weather.entity.HashtagHasBoardEntity;
import com.groupc.weather.entity.primaryKey.HashPk;
@Repository
public interface HashtagHasBoardRepository extends JpaRepository<HashtagHasBoardEntity, HashPk> {
    
   public List<HashtagHasBoardEntity> findByBoardNumber(Integer boardNumber);

   @Transactional
   void deleteById(HashPk hashPk);
   @Transactional
   void deleteByHashtagNumber(Integer hashtagNumber);
}
