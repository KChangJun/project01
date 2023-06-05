package com.groupc.weather.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.groupc.weather.dto.request.user.FindByEmailRequestDto;
import com.groupc.weather.dto.request.user.LoginUserRequestDto;
import com.groupc.weather.dto.request.user.PatchUserRequestDto;
import com.groupc.weather.dto.request.user.PostUserRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "User")

public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer userNumber;
    private String name;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String address;
    private String gender;
    private String profileImageUrl;
    private String birthday;

    public UserEntity(PostUserRequestDto dto) {
        this.name = dto.getUserName();
        this.nickname = dto.getUserNickname();
        this.password = dto.getUserPassword();
        this.email = dto.getUserEmail();
        this.profileImageUrl = dto.getUserProfileImageUrl();
        this.birthday = dto.getUserBirthday();
        this.gender = dto.getUserGender();
        this.address = dto.getUserAddress();
        this.phoneNumber = dto.getUserPhoneNumber();
    }

    public UserEntity(LoginUserRequestDto dto) {
        this.password = dto.getUserPassword();
        this.email = dto.getUserEmail();
    }

    public UserEntity(FindByEmailRequestDto dto) {
        this.name = dto.getUserName();
        this.phoneNumber = dto.getUserPhoneNumber();
    }

    public UserEntity(PatchUserRequestDto dto) {
        this.userNumber = dto.getUserNumber();
    }

}
