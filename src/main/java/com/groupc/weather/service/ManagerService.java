package com.groupc.weather.service;


import org.springframework.http.ResponseEntity;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.manager.ActiveManagerDto;
import com.groupc.weather.dto.request.manager.LoginManagerRequestDto;
import com.groupc.weather.dto.request.manager.PostManagerRequestDto;
import com.groupc.weather.dto.response.manager.LoginManagerResponseDto;

public interface ManagerService {
    public ResponseEntity<ResponseDto> activeManager(AuthenticationObject authenticationObject,ActiveManagerDto dto);;
    public ResponseEntity<ResponseDto> postManager(PostManagerRequestDto dto);
}

