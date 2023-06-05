package com.groupc.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.groupc.weather.entity.ImageUrlEntity;

public interface ImageUrlRepository extends JpaRepository<ImageUrlEntity, Integer> {
    
    public List<ImageUrlEntity> findByBoardNumber(Integer boardNumber);
    
    public ImageUrlEntity findByImageNumber(int imageNumber);
    public ImageUrlEntity findByBoardNumberAndImageNumber(Integer boardNumber, int imageNumber);
    @Transactional
    void deleteByImageNumber(Integer ImageNumber);

}
