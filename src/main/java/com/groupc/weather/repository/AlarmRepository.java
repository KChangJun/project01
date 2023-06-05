package com.groupc.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.weather.entity.AlarmEntity;
@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity,Integer>  {
    
}
