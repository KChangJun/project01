package com.groupc.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.groupc.weather.entity.HashTagEntity;
@Repository
public interface HashtagRepository extends JpaRepository<HashTagEntity, Integer>{

    public HashTagEntity findByHashtagNumber(Integer hashtagNumber);
    


   @Transactional
   void deleteByHashtagNumber(Integer hashtagNumber);


   
}
