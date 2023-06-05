package com.groupc.weather.entity;

import com.groupc.weather.dto.request.board.PostBoardRequestDto2;
import com.groupc.weather.dto.request.common.WeatherDto;
import com.groupc.weather.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

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
@Entity(name="Board")
@Table(name="Board")
public class BoardEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer boardNumber;
        private Integer userNumber; 
        private String title;
        private String content;
        private String writeDatetime;
        private int temperature;
        private String weatherMain;
        private String weatherDescription;
        private int viewCount;

        public BoardEntity(PostBoardRequestDto2 dto, WeatherDto dto2, Integer userNumber) {
                Date now = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String writeDatetime = simpleDateFormat.format(now);

                this.userNumber = userNumber;
                this.title = dto.getTitle();
                this.content = dto.getContent();
                this.temperature = dto2.getTemperature();
                this.weatherDescription = dto2.getWeatherDescription();
                this.weatherMain = dto2.getWeatherMain();
                this.writeDatetime = writeDatetime;
                this.viewCount = 0;
        }

        public BoardEntity(PostBoardRequestDto2 dto, WeatherDto dto2) {
                Date now = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String writeDatetime = simpleDateFormat.format(now);

                this.userNumber = userNumber;
                this.title = dto.getTitle();
                this.content = dto.getContent();
                this.temperature = dto2.getTemperature();
                this.weatherDescription = dto2.getWeatherDescription();
                this.weatherMain=dto2.getWeatherMain();
                this.writeDatetime = writeDatetime;
                this.viewCount = 0;
        }

        // public BoardEntity(String email, PostBoardRequestDto2 dto, WeatherDto dto2) {
        //         Date now = new Date();
        //         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //         String writeDatetime = simpleDateFormat.format(now);
                
        //         this.userNumber = getUserNumber();
        //         this.title = dto.getTitle();
        //         this.content = dto.getContent();
        //         this.temperature = dto2.getTemperature();
        //         this.weatherDescription = dto2.getWeatherDescription();
        //         this.weatherId = dto2.getWeatherId();
        //         this.writeDatetime = writeDatetime;
        //         this.viewCount = 0;
        // }
}
