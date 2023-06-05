package com.groupc.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.groupc.weather.entity.ChattingRoomEntity;
import com.groupc.weather.entity.primaryKey.ChattingRoomPk;


import java.util.List;


@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoomEntity, ChattingRoomPk> {
    public boolean existsByRoomId(String roomId);
    public List<ChattingRoomEntity> findByRoomId(String roomId);
    public List<ChattingRoomEntity> findByUserNumber(Integer userNumber);
   
    @Transactional
    public void deleteById(ChattingRoomPk chattingRoomPk);
    
}