package com.groupc.weather.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.groupc.weather.dto.request.manager.PostManagerRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Manager")
@Table(name = "Manager")
public class ManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer managerNumber;
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String profileImageUrl;
    private boolean active;

    public ManagerEntity(PostManagerRequestDto dto) {

        this.nickname = dto.getNickname();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.phoneNumber = dto.getPhoneNumber();
        this.profileImageUrl = dto.getProfileImageUrl();
        this.active = false;
    }
}
