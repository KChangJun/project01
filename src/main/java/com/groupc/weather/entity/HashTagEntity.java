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
@Entity(name = "hashtag")
@Table(name = "hashtag")
public class HashTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hashtagNumber;
    private String hashtagContent;

    public HashTagEntity(String hashtagContent) {
        this.hashtagContent = hashtagContent;
    }
}