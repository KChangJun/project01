package com.groupc.weather.service;

import org.springframework.http.ResponseEntity;

import com.groupc.weather.dto.response.user.GetTop5FollowerResponseDto;

public interface FollowService {

    public ResponseEntity<? super GetTop5FollowerResponseDto> getTop5List();
}
