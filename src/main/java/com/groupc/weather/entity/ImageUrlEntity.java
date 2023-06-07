package com.groupc.weather.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "imageUrl")
@Table(name = "image_Url")
public class ImageUrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageNumber;
    private String imageUrl;
    private Integer boardNumber;

    public ImageUrlEntity(String imageUrl, Integer boardNumber){
        this.imageUrl = imageUrl;
        this.boardNumber = boardNumber;
    }
    public ImageUrlEntity(int imageNumber, Integer boardNumber){
        this.imageNumber = imageNumber;
        this.boardNumber = boardNumber;
    }

    public ImageUrlEntity(ImageUrlEntity imageList, Integer boardNumbers) {
    }
}
