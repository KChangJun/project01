package com.groupc.weather.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groupc.weather.entity.LikeyEntity;
import com.groupc.weather.entity.primaryKey.LikeyPk;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface LikeyRepository extends JpaRepository<LikeyEntity, LikeyPk> {

   public LikeyEntity deleteByBoardNumber(Integer boardNumber);

   public LikeyEntity findByBoardNumberAndUserNumber(Integer boardNumber, Integer userNumber);
   @Transactional
   public LikeyEntity deleteByBoardNumberAndUserNumber(Integer boardNumber, Integer userNumber);
   public List<LikeyEntity> findByBoardNumber(Integer boardNumber);
   public List<LikeyEntity> findByUserNumber(Integer userNumber);
   
   @Query(
       value = 
       "SELECT * " + 
       "FROM Likey L " +
       "WHERE L.board_number = :boardNumber ",
       nativeQuery = true
       )
       public List<LikeyEntity> findByBoardNumberForLikeyList(@Param("boardNumber") int boardNumber);
       
    

    @Transactional
    public void deleteById(LikeyPk likeyPk);


    @Transactional
    public void deleteAllByUserNumber(Integer userNumber);
}
