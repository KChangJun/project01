package com.groupc.weather.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.groupc.weather.dto.request.follow.FollowRequestDto;
import com.groupc.weather.entity.primaryKey.FollowingPk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// test
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "following")
@Table(name = "following")
@IdClass(FollowingPk.class)
public class FollowingEntity {

    @Id
    private int followerNumber;
    @Id
    private int followingNumber;
    public FollowingEntity(FollowRequestDto dto) {
        this.followerNumber = dto.getFollowerNumber();
        this.followingNumber = dto.getFollowingNumber();
    }
}


