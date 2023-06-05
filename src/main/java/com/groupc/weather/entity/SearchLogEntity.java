package com.groupc.weather.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.groupc.weather.dto.response.board.GetSearchListByWordResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="SearchLog")
@Table(name="Search_Log")
public class SearchLogEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer searchLogNumber;
    String searchWord;
    Integer userNumber;

    public SearchLogEntity(String searchWord, Integer userNumber) {
        this.searchWord = searchWord;
        this.userNumber = userNumber;
    }
}
