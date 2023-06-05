package com.groupc.weather.entity.primaryKey;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Data;

@Data
public class FollowingPk implements Serializable {

    @Column(name = "followerNumber")
    private int followerNumber;

    @Column(name = "followingNumber")
    private int followingNumber;

}
